import java.util.Arrays;
import java.io.Serializable;


class Car implements Vehicle
{
    private String mark;

    private Model[] models;
    
    private class Model implements Serializable
    {
        String name;
        double price;

        public Model(String name, double price) 
        {
            this.name = name;
            this.price = price;
        }
    }
    
    public Car(String mark, int count) 
    {
        this.mark = mark;
        this.models = new Model[count];

        for (int i = 0; i < models.length; i++) 
        {
            models[i] = new Model("model" + (i+1), 500);
        }   
    }

    public String getMark() 
    {
        return mark;
    } 

    public void setMark(String mark) 
    {
        this.mark = mark;
    }

    public int getSize()
    {
        return models.length;
    }

    public String[] getModelNames()
    {
        String[] names = new String[models.length];

        for(int i = 0; i < models.length; i++)
        {
            names[i] = models[i].name;
        }
        return names;
    }

    public double[] getModelPrices()
    {
        double[] prices = new double[models.length];

        for (int i = 0; i < models.length; i++) 
        {
            prices[i] = models[i].price;
        }
        return  prices;
    }

    public double getPriceByName(String name) throws NoSuchModelNameException
    {
        for(int i = 0; i < models.length; i++)
        {
            if(models[i].name.equals(name))
            {
                return models[i].price;
            }
        }
        throw new NoSuchModelNameException("Модель не найдена");
    }

    public void setPriceByName(String name, double newPrice) throws NoSuchModelNameException
    {
        if(newPrice < 0)
        {
            throw new ModelPriceOutOfBoundsException("Цена не может быть отрицательной");
        }
        for (int i = 0; i < models.length; i++) 
        {
            if(models[i].name.equals(name))
            {
                models[i].price = newPrice;
                return;
            }
        }
        throw new NoSuchModelNameException("Модель не найдена");
    }

    public void setModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException
    {
        int targetIndex = -1;
        for (int i = 0; i < models.length; i++) //1
        {
            if (models[i].name.equals(newName)) 
            {
                throw new DuplicateModelNameException("Такое имя уже есть");
            }
            if(models[i].name.equals(oldName))
            {
                targetIndex = i;
            }
        }        
        if(targetIndex == -1)
        {
            throw new NoSuchModelNameException("Модель не найдена");
        }
        models[targetIndex].name = newName;    
    }

    public void addModel(String name,double price) throws DuplicateModelNameException
    {
        if(price < 0)
        {
            throw new ModelPriceOutOfBoundsException("Цена не может быть отрицательной");
        }
        for(int i = 0; i < models.length; i++)
        {
            if (name.equals(models[i].name))
            {
                throw new DuplicateModelNameException("Такое имя уже есть");
            }
        }
        this.models = Arrays.copyOf(this.models, this.models.length + 1);
        this.models[this.models.length - 1] = new Model(name, price);
    }

    public void deleteModel(String name) throws NoSuchModelNameException
    {
        int index = -1;
        for (int i = 0; i < models.length; i++) 
        {
            if (models[i].name.equals(name)) 
            {
                index = i;
                break;
            }
        }

        if (index != -1) 
        {
            System.arraycopy(this.models, index + 1, this.models, index, this.models.length - index - 1);
            this.models = Arrays.copyOf(this.models, this.models.length - 1);
            return;
        }
        throw new NoSuchModelNameException("Модель не найдена");
    }
}