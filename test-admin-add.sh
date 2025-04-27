#!/usr/bin/env bash
set -euo pipefail

# Endpoints
AUTH_URL="http://localhost:8081"
BACKEND_URL="http://localhost:8080"

# Admin credentials (must exist in DB)
ADMIN_EMAIL="admin@admin.com"
ADMIN_PASS="adminpass"

# A unique category name each run
CATEGORY_NAME="AdminOnlyCat-$(date +%s)"

echo "🔐 1) Logging in as ADMIN ($ADMIN_EMAIL)"
LOGIN_RESP=$(curl -s -w "\n%{http_code}" \
  -X POST "$AUTH_URL/api/v1/auth/login" \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"$ADMIN_EMAIL\",\"password\":\"$ADMIN_PASS\"}")
LOGIN_BODY=$(echo "$LOGIN_RESP" | sed '$d')
LOGIN_CODE=$(echo "$LOGIN_RESP" | tail -n1)

if [[ "$LOGIN_CODE" -ne 200 ]]; then
  echo "✗ Admin login failed (HTTP $LOGIN_CODE)" >&2
  echo "$LOGIN_BODY" >&2
  exit 1
fi

ADMIN_TOKEN=$(echo "$LOGIN_BODY" | jq -r .access_token)
echo "✅ Got ADMIN token"

echo
echo "🏷️ 2) Creating category \"$CATEGORY_NAME\" as ADMIN"
CREATE_RESP=$(curl -s -w "\n%{http_code}" \
  -X POST "$BACKEND_URL/categories" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -d "{\"name\":\"$CATEGORY_NAME\"}")
CREATE_BODY=$(echo "$CREATE_RESP" | sed '$d')
CREATE_CODE=$(echo "$CREATE_RESP" | tail -n1)

if [[ "$CREATE_CODE" -eq 200 ]]; then
  echo "✅ Category created: $CATEGORY_NAME"
else
  echo "✗ Category creation failed (HTTP $CREATE_CODE)" >&2
  echo "  Response: $CREATE_BODY"
  exit 1
fi

echo
echo "📋 3) Listing all categories as ADMIN"
curl -s -X GET "$BACKEND_URL/categories" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Accept: application/json" | jq

echo
echo "🎉 Admin-only test complete!"
