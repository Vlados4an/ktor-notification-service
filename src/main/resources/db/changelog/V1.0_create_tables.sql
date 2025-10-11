CREATE TABLE notification
(
    id         SERIAL PRIMARY KEY,
    user_id    INT          NOT NULL,
    type       VARCHAR(30)  NOT NULL,
    title      VARCHAR(255) NOT NULL,
    message    TEXT         NOT NULL,
    is_sent    BOOLEAN      NOT NULL DEFAULT FALSE,
    sent_at    TIMESTAMP    NULL,
    created_at TIMESTAMP    NOT NULL
);


CREATE INDEX idx_notification_user_id ON notification (user_id);
CREATE INDEX idx_notification_created_at ON notification (created_at);

ALTER TABLE notification
    ADD CONSTRAINT chk_notification_type
        CHECK (type IN ('RENTAL_REMINDER', 'RENTAL_OVERDUE', 'NEW_BOOK', 'PENALTY'));

INSERT INTO notification (user_id, type, title, message, is_sent, sent_at, created_at)
VALUES (1, 'RENTAL_REMINDER', 'Напоминание о возврате', 'Верните книгу до завтра', false, NULL, now()),
       (1, 'NEW_BOOK', 'Новая книга', 'Поступила новая книга: Kotlin in Action', true, now() - interval '1 day',
        now() - interval '2 days'),
       (1, 'RENTAL_OVERDUE', 'Просрочка', 'Срок возврата истёк', true, now() - interval '3 days',
        now() - interval '4 days'),
       (1, 'PENALTY', 'Штраф', 'Начислен штраф 3 BYN', false, NULL, now());