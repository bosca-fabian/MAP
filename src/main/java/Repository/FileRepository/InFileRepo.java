package Repository.FileRepository;

import Domain.Validators.EntityAlreadyExistsException;
import Domain.Validators.Validator;
import Repository.Repository;

import java.io.*;
import java.util.*;

public abstract class InFileRepo<Entity extends Domain.Entity> implements Repository<Entity> {

    String fileName;

    Validator<Entity> validator;

    public InFileRepo(String fileName, Validator<Entity> givenValidator) {
        this.fileName = fileName;
        this.validator = givenValidator;
    }


    private void writeToFile(Entity givenEntity){
        try(BufferedWriter bW = new BufferedWriter(new FileWriter(this.fileName, true))){
            bW.write(createEntityAsString(givenEntity));
            bW.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String createEntityAsString(Entity givenEntity);

    protected abstract Entity extractEntity(List<String> attributes);

    private List<Entity> loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<Entity> tempAllEntities = new ArrayList<>();
            String linie;
            while ((linie = br.readLine()) != null) {
                List<String> attr = Arrays.asList(linie.split(";"));
                Entity e = extractEntity(attr);
                tempAllEntities.add(e);
            }
            return tempAllEntities;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void add(Entity entity){
        this.validator.validate(entity);
        if(readByID(entity.getId()) == null)
        {writeToFile(entity);
            }
        else
            throw new EntityAlreadyExistsException("The entity already exists");
    }

    @Override
    public void delete(Entity entity) {
        try {
            List<Entity> allEntities = readAll();
            allEntities.remove(entity);
            PrintWriter pw = new PrintWriter(fileName);
            pw.close();
            for (Entity entities : allEntities)
                writeToFile(entities);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void emptyFile() throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.close();
    }

    protected void writeAllDataToFile(List<Entity> entities){
        for(Entity entity : entities)
            writeToFile(entity);
    }

    @Override
    public Entity readByID(UUID givenID) {
        List<Entity> tempEntitiesList = loadData();
        for(Entity entity : tempEntitiesList)
            if (Objects.equals(givenID, entity.getId()))
                return entity;
        return null;
    }


    @Override
    public List<Entity> readAll() {
        return loadData();
    }

    @Override
    public int size() {
        return loadData().size();
    }
}
