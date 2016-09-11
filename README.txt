Тестовое задание. Книжная картотека

Технологии: EJB, JPA, Transactions, Liquibase, JBoss, XML/JAXB

Имеются карточки книг, каждая из которых содержит ISBN, автора и название книги

Библиотека небольшая: ожидается всего пара тысяч книг

Требуется сервис для хранения картотеки с возможностью поиска по части имени автора или названия

Картотека должна храниться в реляционной базе данных, схема которой должна создаваться при помощи http://www.liquibase.org

Логика работы сервиса должна быть реализована в виде EJB 3.1 Stateless Session Bean, взаимодействие с БД через JPA 

Сервис должен обрабатывать HTTP POST запрос на добавление книги в каталог, и HTTP GET запрос на поиск книг

Сервис должен развертываться в виде EAR на JBoss

Запросы и ответы должны соответствовать схеме: 

<xs:schema attributeFormDefault="unqualified" elementFormDefault="qualified" xmlns:xs="http://www.w3.org/2001/XMLSchema">
  <xs:element name="books" type="bookList"/>
  <xs:complexType name="book">
    <xs:sequence>
      <xs:element type="xs:string" name="isbn"/>
      <xs:element type="xs:string" name="author"/>
      <xs:element type="xs:string" name="title"/>
    </xs:sequence>
  </xs:complexType>
  <xs:complexType name="bookList">
    <xs:sequence>
      <xs:element name="book" type="book" maxOccurs="unbounded" minOccurs="0"/>
    </xs:sequence>
  </xs:complexType>
</xs:schema>
