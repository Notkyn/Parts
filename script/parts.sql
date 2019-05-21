USE test;

DROP TABLE IF EXISTS `part`;
CREATE TABLE `part` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `necessary` BIT(1) NOT NULL DEFAULT b'0',
    `quantity` INT(11) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Система охлаждения для жесткого диска", 0, 2);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Система охлаждения для видеокарты", 0, 8);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Система охлаждения для процесора", 1, 6);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Система охлаждения для корпуса", 0, 4);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Подсветка корпуса", 0, 11);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Корпус", 1, 10);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Блок питания", 1, 9);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("USB на переднюю панель", 0, 0);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("USB на заднюю панель", 0, 7);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Сетевая карта 1 Gb/s", 0, 4);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Сетевая карта 10 Mb/s", 0, 6);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Звуковая карта", 0, 7);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Видеокарта", 0, 17);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Процесор", 1, 63);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("FDD диск", 0, 5);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("DVD-ROM", 0, 12);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("CD-ROM", 0, 6);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("SDD диск", 1, 3);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("HDD диск", 0, 2);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("ОЗУ", 1, 41);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Материнская плата", 1, 11);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Нужная деталь", 1, 23);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Ненужная деталь", 0, 2);
INSERT INTO part (`name`, `necessary`, `quantity`) VALUES ("Очень нужная деталь", 1, 8);