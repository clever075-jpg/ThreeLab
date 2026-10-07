import java.io.Serializable;

public interface Vehicle extends java.io.Serializable
{
    String getMark();
    void setMark(String mark);
    int getSize();
    String[] getModelNames();
    double[] getModelPrices();
    double getPriceByName(String name) throws NoSuchModelNameException;
    void setPriceByName(String name, double newPrice) throws NoSuchModelNameException;
    void setModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException;
    void addModel(String name, double price) throws DuplicateModelNameException;
    void deleteModel(String name) throws NoSuchModelNameException;
}