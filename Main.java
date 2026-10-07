import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("СОЗДАНИЕ ОБЪЕКТОВ");

        Vehicle car = null;
        Vehicle moto = null;

        try {
            car = new Car("Lada", 2);
            moto = new Motorcycle("Yamaha", 3);
        } catch (DuplicateModelNameException e) {
            System.out.println("Ошибка при базовом создании объектов (дубликат): " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка при создании: " + e.getMessage());
            return;
        }

        System.out.print("Создано авто марки: " + car.getMark() + ", моделей: " + car.getSize() + ". Детали: ");
        String[] carNames = car.getModelNames();
        double[] carPrices = car.getModelPrices();
        for (int i = 0; i < car.getSize(); i++) {
            System.out.print(carNames[i] + " (Цена: " + carPrices[i] + ")");
            if (i < car.getSize() - 1) System.out.print(", ");
        }
        System.out.println(); 

        System.out.print("Создан мотоцикл марки: " + moto.getMark() + ", моделей: " + moto.getSize() + ". Детали: ");
        String[] motoNames = moto.getModelNames();
        double[] motoPrices = moto.getModelPrices();
        for (int i = 0; i < moto.getSize(); i++) {
            System.out.print(motoNames[i] + " (Цена: " + motoPrices[i] + ")");
            if (i < moto.getSize() - 1) System.out.print(", ");
        }
        System.out.println();

        System.out.println("\nТЕСТ VEHICLE UTILS (ПОЛИМОРФИЗМ)");

        System.out.println("Модели автомобиля:");
        VehicleUtils.printAllNames(car);
        System.out.println("Средняя цена моделей авто: " + VehicleUtils.getAveragePrice(car));

        System.out.println("\nЦены моделей мотоцикла:");
        VehicleUtils.printAllPrices(moto);
        System.out.println("Средняя цена моделей мотоцикла: " + VehicleUtils.getAveragePrice(moto));

        System.out.println("\nТЕСТ ИСКЛЮЧЕНИЙ");

        System.out.println("Попытка добавить модель с отрицательной ценой:");
        try {
            moto.addModel("model2", -1000);
            System.out.println("Эта строка никогда не выведется");
        } catch (ModelPriceOutOfBoundsException e) {
            System.out.println("Ошибка перехвачена(кривая цена): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка");
        }

        System.out.println("\nПопытка добавить модель с существующим именем:");
        try {
            car.addModel("model1", 1000);
            System.out.println("Эта строка никогда не выведется");
        } catch (DuplicateModelNameException e) {
            System.out.println("Ошибка перехвачена(дубликат)");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка");
        }

        System.out.println("\nПопытка удалить несуществующую деталь (Авто):");
        try {
            car.deleteModel("Деталь которой нет");
            System.out.println("Эта строка никогда не выведется");
        } catch (NoSuchModelNameException e) {
            System.out.println("Ошибка перехвачена(не найдено)");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка");
        }

        System.out.println("\nПопытка удалить несуществующую деталь (Мотоцикл):");
        try {
            moto.deleteModel("Деталь которой нет");
            System.out.println("Эта строка никогда не выведется");
        } catch (NoSuchModelNameException e) {
            System.out.println("Ошибка перехвачена(не найдено)");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка");
        }

        System.out.println("\n               ЗАДАНИЕ 1: БАЙТОВЫЕ И СИМВОЛЬНЫЕ ПОТОКИ               ");
        try {
            System.out.println("Запись автомобиля в байтовый файл (byteData.bin)");
            FileOutputStream fos = new FileOutputStream("byteData.bin");
            VehicleUtils.outputVehicle(car, fos);
            fos.close();

            System.out.println("Чтение автомобиля из байтового файла");
            FileInputStream fis = new FileInputStream("byteData.bin");
            Vehicle restoredCarBytes = VehicleUtils.inputVehicle(fis);
            fis.close();
            
            System.out.print("Восстановлено из байт: Марка = " + restoredCarBytes.getMark() + ", моделей: " + restoredCarBytes.getSize() + ". Детали: ");
            String[] resCarNames = restoredCarBytes.getModelNames();
            double[] resCarPrices = restoredCarBytes.getModelPrices();
            for (int i = 0; i < restoredCarBytes.getSize(); i++) {
                System.out.print(resCarNames[i] + " (" + resCarPrices[i] + ")");
                if (i < restoredCarBytes.getSize() - 1) System.out.print(", ");
            }
            System.out.println();

            System.out.println("\nЗапись мотоцикла в текстовый файл (textData.txt)");
            PrintWriter pw = new PrintWriter(new FileWriter("textData.txt"));
            VehicleUtils.writeVehicle(moto, pw);
            pw.close();

            System.out.println("Чтение мотоцикла из текстового файла");
            BufferedReader br = new BufferedReader(new FileReader("textData.txt"));
            Vehicle restoredMotoText = VehicleUtils.readVehicle(br);
            br.close();
            
            System.out.print("Восстановлено из текста: Марка = " + restoredMotoText.getMark() + ", моделей: " + restoredMotoText.getSize() + ". Детали: ");
            String[] resMotoNames = restoredMotoText.getModelNames();
            double[] resMotoPrices = restoredMotoText.getModelPrices();
            for (int i = 0; i < restoredMotoText.getSize(); i++) {
                System.out.print(resMotoNames[i] + " (" + resMotoPrices[i] + ")");
                if (i < restoredMotoText.getSize() - 1) System.out.print(", ");
            }
            System.out.println();

            System.out.println("\nВывод автомобиля в консоль через символьный поток (SO):");
            PrintWriter consoleWriter = new PrintWriter(System.out);
            VehicleUtils.writeVehicle(car, consoleWriter);

        } catch (Exception e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }

        System.out.println("\n                ЗАДАНИЕ 2: СЕРИАЛИЗАЦИЯ                  ");
        try {
            System.out.println("Сохраняем мотоцикл в файл moto.ser");
            FileOutputStream fos = new FileOutputStream("moto.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(moto);
            oos.close();
            System.out.println("Успешно сохранено");

            System.out.println("\nВосстанавливаем мотоцикл из файла");
            FileInputStream fis = new FileInputStream("moto.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Vehicle restoredMoto = (Vehicle) ois.readObject();
            ois.close();
            System.out.println("Успешно восстановлено");

            System.out.println("\nПРОВЕРКА ДАННЫХ:");
            
            System.out.print("Исходный мотоцикл: Марка = " + moto.getMark() + ", кол-во деталей = " + moto.getSize() + ". Детали: ");
            for (int i = 0; i < moto.getSize(); i++) {
                System.out.print(motoNames[i] + " (" + motoPrices[i] + ")");
                if (i < moto.getSize() - 1) System.out.print(", ");
            }
            System.out.println();

            System.out.print("Восстановленный:   Марка = " + restoredMoto.getMark() + ", кол-во деталей = " + restoredMoto.getSize() + ". Детали: ");
            String[] serMotoNames = restoredMoto.getModelNames();
            double[] serMotoPrices = restoredMoto.getModelPrices();
            for (int i = 0; i < restoredMoto.getSize(); i++) {
                System.out.print(serMotoNames[i] + " (" + serMotoPrices[i] + ")");
                if (i < restoredMoto.getSize() - 1) System.out.print(", ");
            }
            System.out.println();

            Motorcycle original = (Motorcycle) moto;
            Motorcycle restored = (Motorcycle) restoredMoto;
            System.out.println("\nИсходный lastModified: " + original.getLastModified());
            System.out.println("Восстановленный lastModified: " + restored.getLastModified() + " (обнулился из-за transient)");

        } catch (Exception e) {
            System.out.println("Ошибка при сериализации: " + e.getMessage());
        }

        System.out.println("\n                РАБОТА С КОНСОЛЬЮ                 ");
        try {
            System.out.println("Введите данные (Класс, Марка, кол-во деталей, затем построчно имя детали и цена):");
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            Vehicle consoleVehicle = VehicleUtils.readVehicle(consoleReader);

            System.out.println("\nВывод введенного объекта обратно в консоль:");
            OutputStreamWriter consoleWriter = new OutputStreamWriter(System.out);
            VehicleUtils.writeVehicle(consoleVehicle, consoleWriter);
        } catch (Exception e) {
            System.out.println("Ошибка при работе с консолью: " + e.getMessage());
        }
    }
}