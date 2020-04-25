package part1.lesson02.task03;

/**
 * Names.
 * Список имён с привязкой к полу
 *
 * @author Aydar_Safiullin
 */
public enum Names {
    MARAT(Sex.MAN, "Марат"), ANTON(Sex.MAN, "Aнтон"), GUZEL(Sex.WOMAN, "Гузель"),
    AYDAR(Sex.MAN, "Айдар"), ILDAR(Sex.MAN, "Ильдар"), ALIYA(Sex.WOMAN, "Алия"),
    IRINA(Sex.WOMAN, "Ирина"), MARINA(Sex.WOMAN, "Марина"), EVGENIY(Sex.MAN, "Евгений"),
    TIMUR(Sex.MAN, "Тимур"), SVETLANA(Sex.WOMAN, "Светлана"), FANZIL(Sex.MAN, "Фанзиль"),
    IVAN(Sex.MAN, "Иван"), NADEZHDA(Sex.WOMAN, "Надежда"), SEMYON(Sex.MAN, "Семён"),
    AZAT(Sex.MAN, "Азат"), MARGARITA(Sex.WOMAN, "Маргарита"), RAIL(Sex.MAN, "Раиль"),
    OLGA(Sex.WOMAN, "Ольга"), ELENA(Sex.WOMAN, "Елена"), DYANA(Sex.WOMAN, "Диана"),
    NAILYA(Sex.WOMAN, "Наиля"), KONSTANTIN(Sex.MAN, "Константин"), IGOR(Sex.MAN, "Игорь"),
    JOHN(Sex.MAN, "Джон"), SCARLET(Sex.WOMAN, "Скарлет"), ROBERT(Sex.MAN, "Роберт"),
    ALEX(Sex.MAN, "Алекс"), GENIFER(Sex.WOMAN, "Дженифер"), INGA(Sex.WOMAN, "Инга"),
    MARY(Sex.WOMAN, "Мэри"), ARNOLD(Sex.MAN, "Арнольд"), TIM(Sex.MAN, "Тим"),
    KIM(Sex.WOMAN, "Ким"), MURTAZA(Sex.MAN, "Муртаза"), RADIY(Sex.MAN, "Радий"),
    NANCY(Sex.WOMAN, "Нэнси"), SAM(Sex.MAN, "Сэм"), ALAN(Sex.MAN, "Алан"),
    MOLLY(Sex.WOMAN, "Молли"), RAYAN(Sex.MAN, "Райан"), LINDSEY(Sex.WOMAN, "Линдси"),
    BRITNEY(Sex.WOMAN, "Бритни"), VICTORIA(Sex.WOMAN, "Виктория"), ANASTASIA(Sex.WOMAN, "Настя"),
    KEN(Sex.MAN, "Кен");

    private Sex sex;
    private String name;

    Names(Sex sex, String name) {
        this.sex = sex;
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }
}
