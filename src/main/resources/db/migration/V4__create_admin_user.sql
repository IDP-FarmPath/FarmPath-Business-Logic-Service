-- 1) Insert an admin user with a known bcrypt’d password
INSERT INTO project.users (id, username, email, password)
VALUES (
           999,
           'admin',
           'admin@admin.com',
           '$2b$10$yzGliaoej/MSiW9OITm5IuSoEHyeZEhjd/94NKT7PT6XzoLekg1OC'  -- bcrypt("adminpass")
       );

-- 2) Grant ADMIN role (role_id = 1)
INSERT INTO project.user_roles (user_id, role_id)
VALUES (999, 1);
