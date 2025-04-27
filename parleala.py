import requests
import sys

BASE_URL = "http://localhost:8081"
AUTH_ENDPOINT = f"{BASE_URL}/api/v1/auth"
CATEGORY_ENDPOINT = f"{BASE_URL}/categories"
EQUIPMENT_ENDPOINT = f"{BASE_URL}/equipment"

# === SET YOUR EMAIL FOR TESTING MAIL ===
EMAIL_REAL = "ssasdasdnb@gmail.com"  # <-- pune adresa ta aici

# === REGISTER user ===
register_user = {
    "username": "ionels213",
    "email": EMAIL_REAL,
    "password": "test123"
}
print(f"Registering user with email {EMAIL_REAL}...")
requests.post(f"{AUTH_ENDPOINT}/register", json=register_user)

# === LOGIN ===
def login(email, password):
    resp = requests.post(f"{AUTH_ENDPOINT}/login", json={
        "email": email,
        "password": password
    })

    print(f"Login response for {email}: {resp.status_code}")
    print(resp.text)

    if resp.status_code != 200:
        print(f"Login failed for {email}")
        sys.exit(1)

    token = resp.json().get("access_token")
    if not token:
        print("No access_token found in response!")
        sys.exit(1)

    return token

user_token = login(EMAIL_REAL, "test123")
admin_token = login("admin@admin.com", "admin")

print("User token:", user_token[:30] + "...")
print("Admin token:", admin_token[:30] + "...")

headers_admin = {"Authorization": f"Bearer {admin_token}"}

BASE_URL = "http://localhost:8080"
# === ADD CATEGORY ===
category_payload = {"name": "Tractoare"}
resp = requests.post(CATEGORY_ENDPOINT, json=category_payload, headers=headers_admin)
print("Add category:", resp.status_code)
print(resp.text)
if resp.status_code != 200:
    print("Failed to add category!")
    sys.exit(1)

category = resp.json()
category_id = category["id"]

# === ADD EQUIPMENT ===
equipment_payload = {
    "name": "Tractor John Deere",
    "description": "Putere 110CP, motor diesel",
    "pricePerDay": 250.0,
    "categoryId": category_id
}
resp = requests.post(EQUIPMENT_ENDPOINT, json=equipment_payload, headers=headers_admin)
print("Add equipment:", resp.status_code)
print(resp.text)
if resp.status_code != 200:
    print("Failed to add equipment!")
    sys.exit(1)

equipment = resp.json()
equipment_id = equipment["id"]

# === GET ALL EQUIPMENT ===
resp = requests.get(EQUIPMENT_ENDPOINT)
try:
    print("Equipment list:", resp.json())
except Exception as e:
    print("Failed to parse equipment list:", resp.status_code, resp.text)

# === UPDATE EQUIPMENT ===
update_payload = {
    "name": "Tractor Case IH",
    "description": "120CP, motor diesel, 4x4",
    "pricePerDay": 300.0,
    "categoryId": category_id
}
resp = requests.put(f"{EQUIPMENT_ENDPOINT}/{equipment_id}", json=update_payload, headers=headers_admin)
print("Update equipment:", resp.status_code)
print(resp.text)
if resp.status_code != 200:
    print("Failed to update equipment!")
    sys.exit(1)
print("\n✅ Totul a fost testat cu succes.")
