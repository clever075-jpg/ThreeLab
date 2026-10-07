import java.io.*;
import java.util.Scanner;

class VehicleUtils
{
    public static double getAveragePrice(Vehicle vehicle)
    {
        double[] resultVehicle = vehicle.getModelPrices();
        double sum = 0;

        for(int i = 0; i < resultVehicle.length; i++)
        {
            sum = sum + resultVehicle[i];
        }
        sum = sum / vehicle.getSize();
        return sum;
    }

    public static void printAllNames(Vehicle vehicle)
    {
        String[] names = vehicle.getModelNames();

        for(int i = 0; i < names.length; i++)
        {
            System.out.println("Имя: " + names[i]);
        }
    }

    public static void printAllPrices(Vehicle vehicle)
    {
        double[] prices = vehicle.getModelPrices();

        for(int i = 0; i < prices.length; i++)
        {
            System.out.println("Цена: " + prices[i]);
        }  
    }

    // public static void printVehicle(Vehicle vehicle){
    //     System.out.println("Марка: " + vehicle.getMark());
    //     System.out.println("Количество моделей: " + vehicle.getSize());
    //     System.out.println("Модели: ");
    //     try {
    //         for (String name : vehicle.getModelNames()) {
    //         System.out.println(name + " Цена: " + vehicle.getPriceByName(name));
    //     }
    //     } catch (Exception ignored){}
    // }

    public static void outputVehicle(Vehicle v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        byte[] classBytes = v.getClass().getSimpleName().getBytes();
        dos.writeInt(classBytes.length);
        dos.write(classBytes);

        byte[] markBytes = v.getMark().getBytes();
        dos.writeInt(markBytes.length);
        dos.write(markBytes);

        int count = v.getSize();
        dos.writeInt(count);

        String[] names = v.getModelNames();
        double[] prices = v.getModelPrices();
        for (int i = 0; i < count; i++) {
            byte[] nameBytes = names[i].getBytes();
            dos.writeInt(nameBytes.length);
            dos.write(nameBytes);
            dos.writeDouble(prices[i]);
        }
    }

    public static Vehicle inputVehicle(InputStream in) throws IOException, DuplicateModelNameException {
        DataInputStream dis = new DataInputStream(in);

        int classLen = dis.readInt();
        byte[] classBytes = new byte[classLen];
        dis.read(classBytes);
        String className = new String(classBytes);

        int markLen = dis.readInt();
        byte[] markBytes = new byte[markLen];
        dis.read(markBytes);
        String mark = new String(markBytes);

        int count = dis.readInt();

        Vehicle v;
        switch (className) {
            case "Car":
                v = new Car(mark, 0);
                break;
            case "Motorcycle":
                v = new Motorcycle(mark, 0);
                break;
            default:
                throw new IOException("Неизвестный тип транспортного средства: " + className);
        }

        for (int i = 0; i < count; i++) {
            int nameLen = dis.readInt();
            byte[] nameBytes = new byte[nameLen];
            dis.read(nameBytes);
            String name = new String(nameBytes);
            
            double price = dis.readDouble();
            v.addModel(name, price);
        }

        return v;
    }

    public static void writeVehicle(Vehicle v, Writer out) {
        PrintWriter pw = new PrintWriter(out);
        
        pw.println(v.getClass().getSimpleName()); 
        pw.println(v.getMark());
        pw.println(v.getSize());
        

        String[] names = v.getModelNames();
        double[] prices = v.getModelPrices();
        
        for (int i = 0; i < v.getSize(); i++) {
            pw.println(names[i]);
            pw.println(prices[i]);
        }
        pw.flush(); 
    }

    public static Vehicle readVehicle(Reader in) throws IOException, DuplicateModelNameException {
        BufferedReader br = new BufferedReader(in);

        String className = br.readLine();
        String mark = br.readLine();
        int count = Integer.parseInt(br.readLine()); 

        Vehicle v;
        switch (className) {
            case "Car":
                v = new Car(mark, 0);
                break;
            case "Motorcycle":
                v = new Motorcycle(mark, 0);
                break;
            default:
                throw new IOException("Неизвестный класс: " + className);
        }

        for (int i = 0; i < count; i++) {
            String name = br.readLine();
            double price = Double.parseDouble(br.readLine()); 
            v.addModel(name, price);
        }

        return v;
    }
}