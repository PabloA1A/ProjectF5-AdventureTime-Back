-- Roles
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_USER');
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_ADMIN');

-- Users
INSERT INTO users (user_id, username, password) VALUES (default, 'pepe', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO');
INSERT INTO users (user_id, username, password) VALUES (default, 'pepa', '$2a$12$8LegtLQWe717tIPvZeivjuqKnaAs5.bm0Q05.5GrAmcKzXw2NjoUO');

-- Profiles
INSERT INTO profiles (id_profile, email, user_id) VALUES
(default, 'pepe@mail.com', 1),
(default, 'pepa@mail.com', 2);

-- Roles_Users (Ensure role_id and user_id are correctly mapped)
INSERT INTO roles_users (role_id, user_id) VALUES
(1, 1),  -- Assign 'ROLE_USER' to user 'pepe'
(2, 2);  -- Assign 'ROLE_ADMIN' to user 'pepa'

-- EVENTS
INSERT INTO events (id_event, title, description, image_url, event_datetime, max_participants, is_available, is_featured) VALUES
(default, 'Mountain Hiking Adventure', 'Join us for a thrilling mountain hike!', 'https://example.com/images/hiking.jpg', '2024-09-15 08:00:00', 50, true, true),
(default, 'Scuba Diving Expedition', 'Explore the depths of the ocean with our expert guides.', 'https://example.com/images/diving.jpg', '2024-10-01 09:00:00', 30, true, false),
(default, 'Desert Safari', 'Experience the beauty of the desert with our guided safari.', 'https://example.com/images/safari.jpg', '2024-11-05 06:00:00', 20, true, true),
(default, 'City Cycling Tour', 'Discover the city on two wheels with our guided cycling tour.', 'https://example.com/images/cycling.jpg', '2024-09-25 07:00:00', 25, true, false);

-- PARTICIPANTS
INSERT INTO participants (id_participant, joined_at, event_id, user_id) VALUES
(default, '2024-08-20 10:00:00', 1, 1),
(default, '2024-08-21 11:00:00', 1, 2),
(default, '2024-08-22 12:00:00', 2, 1),
(default, '2024-08-23 13:00:00', 3, 2),
(default, '2024-08-24 14:00:00', 4, 1),
(default, '2024-08-25 15:00:00', 2, 1),
(default, '2024-08-26 16:00:00', 3, 2);
