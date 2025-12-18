-- init.sql
CREATE DATABASE IF NOT EXISTS fortune_service;
USE fortune_service;

CREATE TABLE IF NOT EXISTS quotes (
                                      quote_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      text TEXT NOT NULL
);

-- 더미 데이터
INSERT INTO quotes (text) VALUES ('삶이 있는 한 희망은 있다 - 키케로');
INSERT INTO quotes (text) VALUES ('산다는것 그것은 치열한 전투이다. - 로망로랑');
INSERT INTO quotes (text) VALUES ('하루에 3시간을 걸으면 7년 후에 지구를 한바퀴 돌 수 있다. - 사무엘존슨');
INSERT INTO quotes (text) VALUES ('언제나 현재에 집중할수 있다면 행복할것이다. - 파울로 코엘료');
INSERT INTO quotes (text) VALUES ('진정으로 웃으려면 고통을 참아야하며 , 나아가 고통을 즐길 줄 알아야 해 - 찰리 채플린');
INSERT INTO quotes (text) VALUES ('피할 수 없으면 즐겨라 - 로버트 엘리엇');
INSERT INTO quotes (text) VALUES ('단순하게 살아라. 현대인은 쓸데없는 절차와 일 때문에 얼마나 복잡한 삶을 살아가는가? - 이드리스 샤흐');