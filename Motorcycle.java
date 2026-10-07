import java.io.Serializable;

class Motorcycle implements Vehicle
{
    private class Model implements Serializable
    {
        String name = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        public Model(String name, double price) 
        {
            this.name = name;
            this.price = price;
        }

        public Model() { }
    }

    private String mark;
    private int size = 0;
    private Model head;
    private transient long lastModified;

    {
        lastModified = System.currentTimeMillis();
    }

    public Motorcycle(String mark, int count) throws DuplicateModelNameException
    {
        this.mark = mark;

        this.head = new Model();
        this.head.next = head;
        this.head.prev = head;

        for (int i = 0; i < count; i++) 
        {
            addModel("model" + (i + 1), 500);
        }
    }
    public String getMark()
    {
        return mark;
    }
    public void setMark(String mark)
    {
        this.mark = mark;
        this.lastModified = System.currentTimeMillis();
    }
    public int getSize()
    {
        return size;
    }
    public long getLastModified()
    {
        return lastModified;
    }
    public void addModel(String name, double price) throws DuplicateModelNameException
    {
        if(price < 0)
        {
            throw new ModelPriceOutOfBoundsException("Цена не может быть отрицательной");
        }
        Model p = head.next;
        while(p != head)
        {
            if(p.name.equals(name))
            {
                throw new DuplicateModelNameException("Такое имя уже есть");
            }
            p = p.next;
        }

        Model node = new Model(name, price);
        node.next = head;
        node.prev = head.prev;
        head.prev.next = node;
        head.prev = node;
        size++;
        this.lastModified = System.currentTimeMillis();
    }
    public String[] getModelNames()
    {
        String[] names = new String[size];

        Model p = head.next;
        int i = 0;

        while (p != head)
        {
            names[i] = p.name;
            p = p.next;
            i++;
        }
        return names;
    }
    public double[] getModelPrices()
    {
        double[] prices = new double[size];

        Model p = head.next;
        int i = 0;

        while (p != head)
        {
            prices[i] = p.price;
            p = p.next;
            i++;
        }
        return prices;
    }
    public double getPriceByName(String name) throws NoSuchModelNameException
    {
        Model p = head.next;
        while(p != head)
        {
            if (p.name.equals(name)) 
            { 
                return p.price;   
            }
            p = p.next;
        }
        throw new NoSuchModelNameException("Модель не найдена");
    }
    public void setPriceByName(String name, double newPrice) throws NoSuchModelNameException
    {
        Model p = head.next;
        if(newPrice < 0)
        {
            throw new ModelPriceOutOfBoundsException("Цена не может быть отрицательной!");
        }
        while(p !=head)
        {
            if(p.name.equals(name))
            {
                p.price = newPrice;
                this.lastModified = System.currentTimeMillis();
                return;
            }
            p = p.next;
        }
        throw new NoSuchModelNameException("Модель не найдена");
    }
    public void setModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException
    {
        Model targetNode = null;
        Model p = head.next;

        while(p != head)
        {
            if(p.name.equals(newName))
            {
                throw new DuplicateModelNameException("Такое имя уже есть");
            }
            if(p.name.equals(oldName))
            {
                targetNode = p;
            }
            p = p.next;
        }
        if(targetNode == null)
        {
            throw new NoSuchModelNameException("Модель не найдена");
        }
        targetNode.name = newName;
        this.lastModified = System.currentTimeMillis();
    }
    public void deleteModel(String name) throws NoSuchModelNameException
    {
        Model p = head.next;
        while(p != head)
        {
            if(p.name.equals(name))
            {
                p.prev.next = p.next;
                p.next.prev = p.prev;
                size = size - 1;
                this.lastModified = System.currentTimeMillis();
                return;
            }
            p = p.next;
        }
        throw new NoSuchModelNameException("Модель не найдена"); 
    }
}