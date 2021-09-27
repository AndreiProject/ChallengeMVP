# Challenge

Android приложение, идея которого состоит в том, что бы пользователь видел, разделы с разными увлечениями, вызовами и помечал для себя уже те, что ему удалось реализовать или хотелось бы реализовать в будущем. Так он всегда будет в курсе своих текущих достижений и смело может планировать новые. Таким образом развиваясь и повышая свой интерес к новым увлечениям.    

![alt text](https://github.com/AndreiProject/ChallengeMVP/blob/stable/Challenge/app/screenshot/%D1%81ategories_form.png)
![alt text](https://github.com/AndreiProject/ChallengeMVP/blob/stable/Challenge/app/screenshot/category_form.png)
![alt text](https://github.com/AndreiProject/ChallengeMVP/blob/stable/Challenge/app/screenshot/navigation_form.png)
![alt text](https://github.com/AndreiProject/ChallengeMVP/blob/stable/Challenge/app/screenshot/collection_form.png)

Касаемо реализации, упор был сделан на архитектурные решения, с целью разбить приложение на модули, и разные зоны ответственности, придерживаясь основных подходов проектирования. 
Т.о текущая реализация, представляет собой первоначальное MVP, которое в дальнейшем будет развиваться. 

На текущий момент по приложению подготовлена начальная архитектура, наброски основных форм, навигация, настроены основные библиотеки.  
  
  
___Архитектура___: в основе проекта заложен подход Clean Architecture. Взаимодействие UI с бизнес логикой осуществляется посредствам паттерна MVP
   
   
___Основные формы___:
 * LoginActivity - форма логина
 * MainActivity - основная активность, которая содержит навигацию по разным разделам
 * CategoryListFragment - отображает список доступных категорий
 * CategoryFragment - карточка категории, содержит список с разными увлечениями/вызовами
 * CollectionFragment - коллекция достижений пользователя
 * PlannerFragment - запланированные достижения
 * UserStatisticsFragment - содержит статистику по достижениям
 * SettingsFragment - форма настроек 
 * ArithmeticActivity - тестовая форма
 
___Навигация___: реализована с использованием библиотеки Сicerone
 
 
___Основные библиотеки___: Room, Rxjava3, Retrofit2, Firebase, Dagger2, Moxy 
 
 
[Ссылка на релизную сборку apk](https://github.com/AndreiProject/ChallengeMVP/tree/stable/Challenge/app/build/outputs/apk/release)