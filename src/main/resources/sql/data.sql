INSERT INTO concert(`title`, `created_at`, `updated_at`) VALUES ('아이유', now(), now()), ('디비치', now(), now()),('AKMU', now(), now());

INSERT INTO concert_schedule(`concert_id`, `reservation_start_date`, `concert_start_date`, `concert_end_date`, `reservation_seat`, `remain_of_reservation_seat`,`created_at`, `updated_at`) VALUES (3, '2024-10-16 10:00:00.000000', '2024-10-18 08:00:00.000000', '2024-10-18 10:00:00.000000', 50, 50, NOW(),NOW());

INSERT INTO seat(`concert_schedule_id`, `seat_number`, `fee`, `status`, `created_at`, `updated_at`)
VALUES (1, 1, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 2, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 3, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 4, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 5, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 6, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 7, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 8, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 9, 10000, 'AVAILABLE', NOW(), NOW()),
       (1, 10,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 11,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 12,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 13,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 14,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 15,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 16,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 17,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 18,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 19,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 20,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 21,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 22,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 23,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 24,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 25,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 26,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 27,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 28,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 29,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 30,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 31,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 32,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 33,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 34,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 35,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 36,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 37,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 38,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 39,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 40,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 41,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 42,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 43,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 44,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 45,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 46,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 47,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 48,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 49,10000,  'AVAILABLE', NOW(), NOW()),
       (1, 50,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 1, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 2, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 3, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 4, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 5, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 6, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 7, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 8, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 9, 10000, 'AVAILABLE', NOW(), NOW()),
       (2, 10,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 11,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 12,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 13,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 14,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 15,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 16,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 17,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 18,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 19,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 20,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 21,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 22,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 23,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 24,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 25,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 26,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 27,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 28,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 29,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 30,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 31,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 32,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 33,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 34,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 35,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 36,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 37,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 38,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 39,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 40,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 41,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 42,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 43,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 44,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 45,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 46,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 47,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 48,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 49,10000,  'AVAILABLE', NOW(), NOW()),
       (2, 50,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 1, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 2, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 3, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 4, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 5, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 6, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 7, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 8, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 9, 10000, 'AVAILABLE', NOW(), NOW()),
       (3, 10,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 11,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 12,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 13,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 14,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 15,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 16,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 17,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 18,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 19,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 20,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 21,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 22,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 23,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 24,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 25,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 26,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 27,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 28,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 29,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 30,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 31,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 32,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 33,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 34,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 35,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 36,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 37,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 38,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 39,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 40,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 41,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 42,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 43,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 44,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 45,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 46,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 47,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 48,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 49,10000,  'AVAILABLE', NOW(), NOW()),
       (3, 50,10000,  'AVAILABLE', NOW(), NOW());

INSERT INTO users(`name`, `charge`) values ('test1', 100000), ('test2', 1000);