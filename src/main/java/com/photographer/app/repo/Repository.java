package com.photographer.app.repo;


import com.photographer.app.mapper.*;
import com.photographer.app.modelsNew.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;

import java.util.*;



@Component
public class Repository {
    private SqlSessionFactory sqlSessionFactory;
    private EntityMapper entityMapper;
    private ValueMapper valueMapper;
    private AttributeMapper attributeMapper;
    private EntityListMapper entityListMapper;
    private BlogTextMapper blogTextMapper;
    private ProductMapper productMapper;
    private CartItemMapper cartItemMapper;
    private Reader reader;
    private SqlSession sqlSession;
    private String resource = "mybatis-config.xml";

    public Repository() {
    }


    private boolean initConnection(){
        boolean result = true;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);
            blogTextMapper = sqlSession.getMapper(BlogTextMapper.class);
            productMapper = sqlSession.getMapper(ProductMapper.class);


        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    private void cummitAndCloseConnection(){
        sqlSession.commit();
        sqlSession.close();
    }

    public int generateGuestId(){
        int result = -1;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityMapper = sqlSession.getMapper(EntityMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = entityMapper.newGuest();
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    //---------------------------------- Object User

    public UserDetails loadUserByUsername(String username) {

        return findUserByUsername(username);

    }

    public User findUserById(Long id) {

        User user = new User();
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            valueMapper = sqlSession.getMapper(ValueMapper.class);

            Attribute usernameAtt = attributeMapper.getAttributeByName("username");
            Attribute passwordAtt = attributeMapper.getAttributeByName("password");
            Attribute roleAtt = attributeMapper.getAttributeByName("role");

            Value usernameValue = getValueByIds(id, usernameAtt.getId());
            if (usernameValue == null) return null;

            Value passwordValue = getValueByIds(usernameValue.getEntityId(), passwordAtt.getId());
            Value roleValue = getValueByIds(usernameValue.getEntityId(), roleAtt.getId());

            String[] subStr;
            subStr = roleValue.getValue().split(" ");
            Set<Role> roles = new HashSet<>();
            for (String role : subStr) {
                roles.add(new Role(role));
            }

            user.setId(new Long(usernameValue.getEntityId()));
            user.setUsername(usernameValue.getValue());
            user.setPassword(passwordValue.getValue());
            user.setRoles(roles);
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            user = null;
        }

        return user;

    }

    public List<User> findAllUsers() {
        List<User> returnList = new ArrayList<>();
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);

            EntityList entityList = entityListMapper.getEntityByName("user");

            List<Entity> userList = entityMapper.getAllEntitiesByType(entityList.getId());
            if (userList == null) return null;

            Attribute usernameAtt = attributeMapper.getAttributeByName("username");
            Attribute passwordAtt = attributeMapper.getAttributeByName("password");
            Attribute roleAtt = attributeMapper.getAttributeByName("role");

            for (Entity entity : userList) {
                User user = new User();

                Value usernameValue = getValueByIds(entity.getId(), usernameAtt.getId());

                Value passwordValue = getValueByIds(usernameValue.getEntityId(), passwordAtt.getId());
                Value roleValue = getValueByIds(usernameValue.getEntityId(), roleAtt.getId());

                String[] subStr;
                subStr = roleValue.getValue().split(" ");
                Set<Role> roles = new HashSet<>();
                for (String role : subStr) {
                    roles.add(new Role(role));
                }

                user.setId(new Long(usernameValue.getEntityId()));
                user.setUsername(usernameValue.getValue());
                user.setPassword(passwordValue.getValue());
                user.setRoles(roles);
                returnList.add(user);
            }
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            returnList = null;
        }

        return returnList;

    }

    public boolean saveUser(User user) {
        boolean result = false;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);

            EntityList entityList = entityListMapper.getEntityByName("user");

            Value value = valueMapper.getUserIdByUsername(user.getUsername());
            if (value != null) return false;

            Attribute usernameAtt = attributeMapper.getAttributeByName("username");
            Attribute passwordAtt = attributeMapper.getAttributeByName("password");
            Attribute roleAtt = attributeMapper.getAttributeByName("role");

            Entity entity = new Entity();
            entity.setType_id(entityList.getId());
            entity.setId(entityMapper.insertEntity(entity));

            Value insertValue = new Value();

            insertValue.setEntityId(entity.getId());
            insertValue.setAttId(usernameAtt.getId());
            insertValue.setValue(user.getUsername());
            valueMapper.insertValue(insertValue);

            insertValue.setAttId(passwordAtt.getId());
            insertValue.setValue(user.getPassword());
            valueMapper.insertValue(insertValue);

            String roles = new String();
            for (Role role : user.getRoles()) {
                roles += role.getName() + " ";
            }
            insertValue.setAttId(roleAtt.getId());
            insertValue.setValue(roles);
            valueMapper.insertValue(insertValue);

            sqlSession.commit();
            sqlSession.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();

        }

        return result;

    }

    public boolean deleteUserById(long id) {
        boolean result = false;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);

            EntityList entityList = entityListMapper.getEntityByName("user");
            Entity entity = entityMapper.getEntityById(id);
            if (entity != null) {
                entityMapper.deleteEntityById(entity.getId());
                result = true;
            }

            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return result;

    }

    public int insertEntity(Entity entity) {
        int result = 100;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityMapper = sqlSession.getMapper(EntityMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = entityMapper.insertEntity(entity);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int insertAttribute(Attribute attribute) {
        int result = 100;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = attributeMapper.insertAttribute(attribute);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Attribute> getAttributes() {
        List<Attribute> result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = attributeMapper.getAllAttributes();
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Attribute getAttributeBy(Long id) {
        Attribute result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = attributeMapper.getAttributeById(id);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Attribute getAttributeBy(String name) {
        Attribute result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = attributeMapper.getAttributeByName(name);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int insertValue(Value value) {
        Integer result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = valueMapper.insertValue(value);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    ;

    public int updateValue(Value value) {
        Integer result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = valueMapper.updateValue(value);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    ;

    public Value getValueByIds(Long entityId, Long attId) {
        Value result = null;
        Map<String, Long> map = new HashMap<>();
        map.put("en_id", entityId);
        map.put("att_id", attId);
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = valueMapper.getValueByIds(map);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    ;

    public List<Value> getValues() {

        List<Value> result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = valueMapper.getValues();
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    ;

    public EntityList getEntityById(Integer id) {

        EntityList result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = entityListMapper.getEntityById(id);
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    ;

    public List<EntityList> getAllAvailableEntities() {

        List<EntityList> result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            result = entityListMapper.getAllAvailableEntities();
            sqlSession.commit();
            sqlSession.close();
            //List<Entity> entities = entityMapper.getAllEntities();
            //Entity entity = entityMapper.getEntityById(2);
            //System.out.println(entity);
            //System.out.println("\nLIST");
            //entities.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    public User findUserByUsername(String username) {
        User user = new User();
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            valueMapper = sqlSession.getMapper(ValueMapper.class);

            Attribute passwordAtt = attributeMapper.getAttributeByName("password");
            Attribute roleAtt = attributeMapper.getAttributeByName("role");

            Value usernameValue = valueMapper.getUserIdByUsername(username);
            if (usernameValue == null) return null;

            Value passwordValue = getValueByIds(usernameValue.getEntityId(), passwordAtt.getId());
            Value roleValue = getValueByIds(usernameValue.getEntityId(), roleAtt.getId());

            String[] subStr;
            subStr = roleValue.getValue().split(" ");
            Set<Role> roles = new HashSet<>();
            for (String role : subStr) {
                roles.add(new Role(role));
            }

            user.setId(new Long(usernameValue.getEntityId()));
            user.setUsername(usernameValue.getValue());
            user.setPassword(passwordValue.getValue());
            user.setRoles(roles);
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            user = null;
        }

        return user;

    }


    //---------------------------------- Object BlogPost


    public List<BlogPost> findAllBlogPost() {
        List<BlogPost> list = new ArrayList<>();
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            blogTextMapper = sqlSession.getMapper(BlogTextMapper.class);

            EntityList entityBlogPost = entityListMapper.getEntityByName("blogpost");
            List<Attribute> blogPostAttributes = attributeMapper.getAllAttributesByType(entityBlogPost.getId());
            List<Entity> allBlogPosts = entityMapper.getAllEntitiesByType(entityBlogPost.getId());

            BlogText blogText;
            Value gettingValue;
            for (Entity entity : allBlogPosts) {
                BlogPost gettingBlogPost = new BlogPost();
                blogText = blogTextMapper.getBlogTextById(entity.getId());
                gettingBlogPost.setAnons(blogText.getAnons());
                gettingBlogPost.setText(blogText.getFull_text());
                gettingBlogPost.setId(entity.getId());

                for (Attribute att : blogPostAttributes) {
                    Map<String, Long> map = new HashMap<>();
                    map.put("en_id", entity.getId());
                    map.put("att_id", att.getId());
                    gettingValue = valueMapper.getValueByIds(map);

                    switch (att.getAttName()) {
                        case "blogpost_title": {
                            gettingBlogPost.setTitle(gettingValue.getValue());
                            break;
                        }
                        case "blogpost_datetime": {
                            gettingBlogPost.setDateTime(gettingValue.getValue());
                            break;
                        }
                        case "blogpost_author": {
                            gettingBlogPost.setAuthor(gettingValue.getValue());
                            break;
                        }
                        case "blogpost_vcounter": {
                            gettingBlogPost.setView(new Integer(gettingValue.getValue()));
                            break;
                        }
                    }
                }
                list.add(gettingBlogPost);
            }

            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            list = null;
        }


        return list;
    }

    public int saveBlogPost(BlogPost blogPost) {
        int result = 0;
        try {
            Optional<BlogPost> blogPostAlreadyExist = findBlogPostById(blogPost.getId());
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            blogTextMapper = sqlSession.getMapper(BlogTextMapper.class);

            //Optional<BlogPost> blogPostAlreadyExist = findBlogPostById(blogPost.getId());
            if(blogPostAlreadyExist.isPresent()){
                BlogText updatedBlogText = blogTextMapper.getBlogTextById(blogPost.getId());
                updatedBlogText.setFull_text(blogPost.getText());
                updatedBlogText.setAnons(blogPost.getAnons());
                blogTextMapper.updateBlogText(updatedBlogText);

                Attribute title = attributeMapper.getAttributeByName("blogpost_title");
                Value updatedValue = new Value();
                updatedValue.setValue(blogPost.getTitle());
                updatedValue.setAttId(title.getId());
                updatedValue.setEntityId(blogPost.getId());
                valueMapper.updateValue(updatedValue);

                Attribute views = attributeMapper.getAttributeByName("blogpost_vcounter");

                Value updatedViews = new Value();
                updatedViews.setEntityId(blogPost.getId());
                updatedViews.setAttId(views.getId());
                updatedViews.setValue(String.valueOf(blogPost.getView()));
                valueMapper.updateValue(updatedViews);


                sqlSession.commit();
                sqlSession.close();

                return 1;
            }

            EntityList entityBlogPost = entityListMapper.getEntityByName("blogpost");
            Entity insertedEntity = new Entity();
            insertedEntity.setType_id(entityBlogPost.getId());
            blogPost.setId(new Long(entityMapper.insertEntity(insertedEntity)));
            blogPost.setDateTime(new Timestamp(new Date().getTime()).toString());

            BlogText insertedBlogText = new BlogText();
            insertedBlogText.setId(blogPost.getId());
            insertedBlogText.setAnons(blogPost.getAnons());
            insertedBlogText.setFull_text(blogPost.getText());
            blogTextMapper.insertBlogText(insertedBlogText);

            List<Attribute> blogPostAttributes = attributeMapper.getAllAttributesByType(entityBlogPost.getId());

            Value insertedValue = new Value();
            insertedValue.setEntityId(blogPost.getId());
            for (Attribute att : blogPostAttributes) {
                insertedValue.setAttId(att.getId());
                switch (att.getAttName()) {
                    case "blogpost_title": {
                        insertedValue.setValue(blogPost.getTitle());
                        break;
                    }
                    case "blogpost_datetime": {
                        insertedValue.setValue(blogPost.getDateTime());
                        break;
                    }
                    case "blogpost_author": {
                        insertedValue.setValue(blogPost.getAuthor());
                        break;
                    }
                    case "blogpost_vcounter": {
                        insertedValue.setValue(new Integer(blogPost.getView()).toString());
                        break;
                    }
                }
                valueMapper.insertValue(insertedValue);
            }
            sqlSession.commit();
            sqlSession.close();
            result = 1;
        } catch (IOException e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    public boolean blogPostExistsById(long id) {
        boolean result = false;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            blogTextMapper = sqlSession.getMapper(BlogTextMapper.class);

            EntityList entityBlogPost = entityListMapper.getEntityByName("blogpost");
            Entity entity = entityMapper.getEntityById(id);

            sqlSession.commit();
            sqlSession.close();

            if (entity == null) return false;
            if (entity.getType_id() != entityBlogPost.getId()) return false;
            result = true;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return result;
    }

    public Optional<BlogPost> findBlogPostById(long id) {
        BlogPost blogPost = new BlogPost();
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            blogTextMapper = sqlSession.getMapper(BlogTextMapper.class);

            EntityList entityBlogPost = entityListMapper.getEntityByName("blogpost");
            List<Attribute> blogPostAttributes = attributeMapper.getAllAttributesByType(entityBlogPost.getId());
            BlogText blogText = blogTextMapper.getBlogTextById(id);
            Entity entity = entityMapper.getEntityById(id);
            if(entity==null) throw new IOException("Entity not found");


            blogPost.setId(entity.getId());
            blogPost.setAnons(blogText.getAnons());
            blogPost.setText(blogText.getFull_text());
            Value gettingValue;
            for (Attribute att : blogPostAttributes) {
                Map<String, Long> map = new HashMap<>();
                map.put("en_id", entity.getId());
                map.put("att_id", att.getId());
                gettingValue = valueMapper.getValueByIds(map);

                switch (att.getAttName()) {
                    case "blogpost_title": {
                        blogPost.setTitle(gettingValue.getValue());
                        break;
                    }
                    case "blogpost_datetime": {
                        blogPost.setDateTime(gettingValue.getValue());
                        break;
                    }
                    case "blogpost_author": {
                        blogPost.setAuthor(gettingValue.getValue());
                        break;
                    }
                    case "blogpost_vcounter": {
                        blogPost.setView(new Integer(gettingValue.getValue()));
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            blogPost = null;
        }
        finally {
            sqlSession.commit();
            sqlSession.close();
        }


        return Optional.ofNullable(blogPost);
    }


    //----------------------------------Object Product

    public List<Product> findAllProducts(){
        List<Product> list = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            productMapper = sqlSession.getMapper(ProductMapper.class);

            list = productMapper.getAllProducts();
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            list = null;
        }

        return list;
    }

    public Product findProductById(long id){
        Product result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            productMapper = sqlSession.getMapper(ProductMapper.class);

            result = productMapper.getProductById(id);
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    public int saveProduct(Product product){
        int result = 0;
        try {
            Product productFromDB = findProductById(product.getId());
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class);
            entityMapper = sqlSession.getMapper(EntityMapper.class);
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);
            valueMapper = sqlSession.getMapper(ValueMapper.class);
            productMapper = sqlSession.getMapper(ProductMapper.class);

            if(productFromDB!=null){
                Value updatedValue = new Value();

                Attribute name = attributeMapper.getAttributeByName("product_name");
                updatedValue.setValue(product.getName());
                updatedValue.setAttId(name.getId());
                updatedValue.setEntityId(product.getId());
                valueMapper.updateValue(updatedValue);

                Attribute price = attributeMapper.getAttributeByName("product_price");
                updatedValue.setValue(String.valueOf(product.getPrice()));
                updatedValue.setAttId(price.getId());
                valueMapper.updateValue(updatedValue);

                Attribute active = attributeMapper.getAttributeByName("product_active");
                updatedValue.setValue(String.valueOf(product.isActive()));
                updatedValue.setAttId(active.getId());
                valueMapper.updateValue(updatedValue);

                Attribute description = attributeMapper.getAttributeByName("product_description");
                updatedValue.setValue(product.getDescription());
                updatedValue.setAttId(description.getId());
                valueMapper.updateValue(updatedValue);



                sqlSession.commit();
                sqlSession.close();

                return 1;
            }

            EntityList entityProduct = entityListMapper.getEntityByName("product");
            Entity insertedEntity = new Entity();
            insertedEntity.setType_id(entityProduct.getId());
            product.setId(entityMapper.insertEntity(insertedEntity));

            List<Attribute> blogPostAttributes = attributeMapper.getAllAttributesByType(entityProduct.getId());

            Value insertedValue = new Value();
            insertedValue.setEntityId(product.getId());
            for (Attribute att : blogPostAttributes) {
                insertedValue.setAttId(att.getId());
                switch (att.getAttName()) {
                    case "product_name": {
                        insertedValue.setValue(product.getName());
                        break;
                    }
                    case "product_price": {
                        insertedValue.setValue(String.valueOf(product.getPrice()));
                        break;
                    }
                    case "product_active": {
                        insertedValue.setValue(String.valueOf(product.isActive()));
                        break;
                    }
                    case "product_description": {
                        insertedValue.setValue(product.getDescription());
                        break;
                    }
                }
                valueMapper.insertValue(insertedValue);
            }
            sqlSession.commit();
            sqlSession.close();
            result = 1;
        } catch (IOException e) {
            e.printStackTrace();
            result = 0;
        }

        return result;

    }

    public int deleteProductById(long id){
        int result;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityMapper = sqlSession.getMapper(EntityMapper.class);

            result = entityMapper.deleteEntityById(id);
            sqlSession.commit();
            sqlSession.close();
        } catch (IOException e) {
            e.printStackTrace();
            result = 0;
        }

        return  result;
    }

    //----------------------------------Cart

    public List<CartItem> getCartItemByUserId(long id){
        List<CartItem> cartItems = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            cartItems = cartItemMapper.getUserCartItems(id);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public List<CartItem> getCartItemByGuestId(long id){
        List<CartItem> cartItems = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            cartItems = cartItemMapper.getGuestCartItems(id);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void updateCartItem(CartItem cartItem){
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //Читаем файл с настройками подключения и настройками MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class); //Создаем маппер, из которого и будем вызывать методы getSubscriberById и getSubscribers
            cartItemMapper.updateCartItem(cartItem);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
