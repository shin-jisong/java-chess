DROP TABLE IF EXISTS piece;

create table piece
(
    game_id INT,
    piece_type VARCHAR(10) NOT NULL,
    color VARCHAR(10) NOT NULL,
    `column` VARCHAR(10) NOT NULL,
    `row` INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (game_id)
)
