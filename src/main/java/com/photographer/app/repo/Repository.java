package com.photographer.app.repo;


import com.photographer.app.mapper.*;
import com.photographer.app.model.*;
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
    private AlbumMapper albumMapper;
    private OrderMapper orderMapper;
    private ImagesMapper imagesMapper;
    private Reader reader;
    private SqlSession sqlSession;
    private String resource = "mybatis-config.xml";


    public Repository() {
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private boolean initConnection() {
        //boolean result = true;
        sqlSession = sqlSessionFactory.openSession();
        attributeMapper = sqlSession.getMapper(AttributeMapper.class);
        valueMapper = sqlSession.getMapper(ValueMapper.class);
        entityMapper = sqlSession.getMapper(EntityMapper.class);
        entityListMapper = sqlSession.getMapper(EntityListMapper.class);
        blogTextMapper = sqlSession.getMapper(BlogTextMapper.class);
        productMapper = sqlSession.getMapper(ProductMapper.class);
        cartItemMapper = sqlSession.getMapper(CartItemMapper.class);
        albumMapper = sqlSession.getMapper(AlbumMapper.class);
        orderMapper = sqlSession.getMapper(OrderMapper.class);
        imagesMapper = sqlSession.getMapper(ImagesMapper.class);
        return true;
    }

    private void commitAndCloseConnection() {
        sqlSession.commit();
        sqlSession.close();
    }

    public int generateGuestId() {
        int result = -1;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityMapper = sqlSession.getMapper(EntityMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = entityMapper.newGuest();
            sqlSession.commit();
            sqlSession.close();

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
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
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
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
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
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
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
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
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
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityMapper = sqlSession.getMapper(EntityMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = entityMapper.insertEntity(entity);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int insertAttribute(Attribute attribute) {
        int result = 100;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = attributeMapper.insertAttribute(attribute);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Attribute> getAttributes() {
        List<Attribute> result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = attributeMapper.getAllAttributes();
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Attribute getAttributeBy(Long id) {
        Attribute result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = attributeMapper.getAttributeById(id);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Attribute getAttributeBy(String name) {
        Attribute result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = attributeMapper.getAttributeByName(name);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int insertValue(Value value) {
        Integer result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = valueMapper.insertValue(value);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }


    public int updateValue(Value value) {
        Integer result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = valueMapper.updateValue(value);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public Value getValueByIds(Long entityId, Long attId) {
        Value result = null;
        Map<String, Long> map = new HashMap<>();
        map.put("en_id", entityId);
        map.put("att_id", attId);
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = valueMapper.getValueByIds(map);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    public List<Value> getValues() {

        List<Value> result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            valueMapper = sqlSession.getMapper(ValueMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = valueMapper.getValues();
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }


    public EntityList getEntityById(Integer id) {

        EntityList result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = entityListMapper.getEntityById(id);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }


    public List<EntityList> getAllAvailableEntities() {

        List<EntityList> result = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            entityListMapper = sqlSession.getMapper(EntityListMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            result = entityListMapper.getAllAvailableEntities();
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    public User findUserByUsername(String username) {
        User user = new User();
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            attributeMapper = sqlSession.getMapper(AttributeMapper.class);//?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
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

    public boolean setUserEmail(User user, String email , String emailConfirmCode){
        initConnection();
        Attribute userEmailAtt = attributeMapper.getAttributeByName("user_email");
        Attribute userConfirmCodeAtt = attributeMapper.getAttributeByName("user_confirm_code");
        Map<String, Long> map = new HashMap<>();
        map.put("en_id", user.getId());
        map.put("att_id", userEmailAtt.getId());

        Value  userEmail = new Value();
        userEmail.setValue(email);
        userEmail.setEntityId(user.getId());
        userEmail.setAttId(userEmailAtt.getId());
        if(valueMapper.getValueByIds(map)==null) {
            valueMapper.insertValue(userEmail);
        } else {
            valueMapper.updateValue(userEmail);
        }

        map.put("att_id", userConfirmCodeAtt.getId());
        Value confirmCode = new Value();
        confirmCode.setValue(emailConfirmCode);
        confirmCode.setEntityId(user.getId());
        confirmCode.setAttId(userConfirmCodeAtt.getId());
        if(valueMapper.getValueByIds(map)==null) {
            valueMapper.insertValue(confirmCode);
        } else {
            valueMapper.updateValue(confirmCode);
        }
        commitAndCloseConnection();
        return true;
    }

    public String getUserEmailConfirmCode(User user){
        initConnection();
        Attribute confirmAtt = attributeMapper.getAttributeByName("user_confirm_code");
        Map<String, Long> map = new HashMap<>();
        map.put("en_id", user.getId());
        map.put("att_id", confirmAtt.getId());
        Value confirmVal = valueMapper.getValueByIds(map);
        commitAndCloseConnection();
        return confirmVal.getValue();
    }

    public void setConfirmedEmail(User user){
        initConnection();

        Attribute emailAtt = attributeMapper.getAttributeByName("user_email");
        Attribute confirmedEmailAtt = attributeMapper.getAttributeByName("user_confirmed_email");

        Map<String, Long> map = new HashMap<>();
        map.put("en_id", user.getId());
        map.put("att_id", emailAtt.getId());
        Value email = valueMapper.getValueByIds(map);
        email.setAttId(confirmedEmailAtt.getId());

        map.put("att_id", confirmedEmailAtt.getId());
        Value confEmail = valueMapper.getValueByIds(map);
        if(confEmail!=null){
            valueMapper.updateValue(email);
        }else{
            valueMapper.insertValue(email);
        }

        commitAndCloseConnection();
    }

    public boolean emailIsConfirmed(User user){
        boolean result=false;
        initConnection();

        Attribute emailAtt = attributeMapper.getAttributeByName("user_email");
        Attribute confirmedEmailAtt = attributeMapper.getAttributeByName("user_confirmed_email");

        Map<String, Long> map = new HashMap<>();
        map.put("en_id", user.getId());
        map.put("att_id", emailAtt.getId());
        Value email = valueMapper.getValueByIds(map);

        map.put("att_id", confirmedEmailAtt.getId());
        Value confEmail = valueMapper.getValueByIds(map);

        if((email!=null)&(confEmail!=null)){
            if(email.getValue().equals(confEmail.getValue())){
                result=true;
            }
        }

        commitAndCloseConnection();
        return  result;
    }
    public String getUserEmail(User user){
        initConnection();
        Attribute emailAtt = attributeMapper.getAttributeByName("user_email");
        Map<String, Long> map = new HashMap<>();
        map.put("en_id", user.getId());
        map.put("att_id", emailAtt.getId());
        Value emailVal = valueMapper.getValueByIds(map);
        String email = "example@post.com";
        if(emailVal!=null){
            email = emailVal.getValue();
        }
        commitAndCloseConnection();
        return email;
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
            if (blogPostAlreadyExist.isPresent()) {
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
            if (entity == null) throw new IOException("Entity not found");


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
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }


        return Optional.ofNullable(blogPost);
    }

    public int deleteBlogPostById(long id) {
        initConnection();
        entityMapper.deleteEntityById(id);
        int result = blogTextMapper.deleteBlogTextById(id);
        commitAndCloseConnection();
        return  result;
    }


    //----------------------------------Object Product

    public List<Product> findAllProducts() {
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

    public Product findProductById(long id) {
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

    public long saveProduct(Product product) {
        long result = 0;
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
            imagesMapper = sqlSession.getMapper(ImagesMapper.class);

            Image image = new Image();
            image.setImagebyte64(product.getImage());
            if (productFromDB != null) {
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

                image.setEn_id(product.getId());
                imagesMapper.updateImage(image);

                sqlSession.commit();
                sqlSession.close();

                return productFromDB.getId();
            }

            EntityList entityProduct = entityListMapper.getEntityByName("product");
            Entity insertedEntity = new Entity();
            insertedEntity.setType_id(entityProduct.getId());
            product.setId(entityMapper.insertEntity(insertedEntity));

            image.setEn_id(product.getId());
            imagesMapper.insertImage(image);

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
            result = insertedEntity.getId();
        } catch (IOException e) {
            e.printStackTrace();
            result = 0;
        }

        return result;

    }

    public int deleteProductById(long id) {
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

        return result;
    }

    //----------------------------------Cart

    public List<CartItem> getCartItemByUserId(long id) {
        List<CartItem> cartItems = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            cartItems = cartItemMapper.getUserCartItems(id);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public List<CartItem> getCartItemByGuestId(long id) {
        List<CartItem> cartItems = null;
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            cartItems = cartItemMapper.getGuestCartItems(id);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public void updateCartItem(CartItem cartItem) {
        try {
            reader = Resources
                    .getResourceAsReader("mybatis-config.xml"); //???????????? ???????? ?? ?????????????????????? ?????????????????????? ?? ?????????????????????? MyBatis
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            SqlSession sqlSession = sqlSessionFactory.openSession();
            cartItemMapper = sqlSession.getMapper(CartItemMapper.class); //?????????????? ????????????, ???? ???????????????? ?? ?????????? ???????????????? ???????????? getSubscriberById ?? getSubscribers
            cartItemMapper.updateCartItem(cartItem);
            sqlSession.commit();
            sqlSession.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int insertCartItem(CartItem cartItem) {
        initConnection();
        int result;
        if (cartItem.getEn_id() < 0) {
            result = cartItemMapper.insertGuestCartItem(cartItem);
        } else {
            result = cartItemMapper.insertUserCartItem(cartItem);
        }
        commitAndCloseConnection();
        return result;
    }

    public CartItem getCartItemById(long id) {
        initConnection();
        CartItem cartItem = cartItemMapper.getCartItemById(id);
        commitAndCloseConnection();
        return cartItem;
    }

    public int deleteCartItemById(long id) {
        initConnection();
        int result = cartItemMapper.deleteCartItemById(id);
        commitAndCloseConnection();
        return result;
    }


    //-------------------------------------Albums

    public List<Album> getAlbums() {
        initConnection();
        List<Album> result = albumMapper.getAlbums();
        commitAndCloseConnection();
        return result;
    }
    public Album getAlbumById(long id){
        initConnection();
        Album album  = albumMapper.getAlbumsById(id);
        commitAndCloseConnection();
        return album;
    }
    public void deleteAlbumById(long id){
        initConnection();
        List<Album> albums = albumMapper.getAlbums();
        List<Album> albumToDelete = new ArrayList<>();
        for(Album album:albums){
            if((album.getId()==id)||(album.getP_id()==id)){
                albumToDelete.add(album);
            }
        }

        for(Album album:albumToDelete){
            entityMapper.deleteEntityById(album.getId());
            imagesMapper.deleteImagesByEnId(album.getId());
        }


        commitAndCloseConnection();
    }
    public void addAlbum(Album album){
        initConnection();
        EntityList galleryAlbum = entityListMapper.getEntityByName("gallery_album");
        Entity newAlbum = new Entity();
        newAlbum.setType_id(galleryAlbum.getId());
        album.setId(entityMapper.insertEntity(newAlbum));

        List<Attribute> albumAtt = attributeMapper.getAllAttributesByType(galleryAlbum.getId());
        for(Attribute att: albumAtt){
            Value insertedAtt = new Value();
            insertedAtt.setEntityId(album.getId());
            insertedAtt.setAttId(att.getId());
            switch (att.getAttName()){
                case "gallery_album_name": { insertedAtt.setValue(album.getName());
                    break;
                }
                case "gallery_album_p_id": { insertedAtt.setValue(String.valueOf(album.getP_id()));
                    break;
                }
            }
            valueMapper.insertValue(insertedAtt);
        }

        commitAndCloseConnection();
    }
    public void editAlbum(Album album){
        initConnection();
        EntityList galleryAlbum = entityListMapper.getEntityByName("gallery_album");
        List<Attribute> albumAtt = attributeMapper.getAllAttributesByType(galleryAlbum.getId());

        for(Attribute att: albumAtt){
            Value insertedAtt = new Value();
            insertedAtt.setEntityId(album.getId());
            insertedAtt.setAttId(att.getId());
            switch (att.getAttName()){
                case "gallery_album_name": { insertedAtt.setValue(album.getName());
                    break;
                }
                case "gallery_album_p_id": { insertedAtt.setValue(String.valueOf(album.getP_id()));
                    break;
                }
            }
            valueMapper.updateValue(insertedAtt);
        }

        commitAndCloseConnection();
    }


    //--------------------------------------Orders
    public boolean createOrder(User user) {
        boolean result = true;
        initConnection();

        List<CartItem> userCartItems = cartItemMapper.getUserCartItems(user.getId());
        if (userCartItems.isEmpty()) {
            commitAndCloseConnection();
            return false;
        }

        double totalCost = 0;
        for (CartItem cartItem : userCartItems) {
            totalCost += productMapper.getProductById(cartItem.getPr_id()).getPrice();
        }

        EntityList orderEntityID = entityListMapper.getEntityByName("order");
        Entity orderEntity = new Entity();
        orderEntity.setType_id(orderEntityID.getId());
        orderEntity.setId(entityMapper.insertEntity(orderEntity));

        List<Attribute> orderAttributes = attributeMapper.getAllAttributesByType(orderEntity.getType_id());
        for (Attribute attribute : orderAttributes) {
            Value attValue = new Value();
            attValue.setAttId(attribute.getId());
            attValue.setEntityId(orderEntity.getId());
            switch (attribute.getAttName()) {
                case "order_uid": {
                    attValue.setValue(String.valueOf(user.getId()));
                    break;
                }
                case "order_datetime": {
                    attValue.setValue(new Timestamp(new Date().getTime()).toString());
                    break;
                }
                case "order_status": {
                    attValue.setValue("??????????");
                    break;
                }
                case "order_total": {
                    attValue.setValue(String.valueOf(totalCost));
                    break;
                }
            }
            valueMapper.insertValue(attValue);

        }

        EntityList orderItemEntityID = entityListMapper.getEntityByName("order_item");
        List<Attribute> orderItemAtt = attributeMapper.getAllAttributesByType(orderItemEntityID.getId());
        for (CartItem cartItem : userCartItems) {
            Entity orderItem = new Entity();
            orderItem.setType_id(orderItemEntityID.getId());
            orderItem.setId(entityMapper.insertEntity(orderItem));
            Product currProduct  = productMapper.getProductById(cartItem.getPr_id());
            for (Attribute attribute : orderItemAtt) {
                Value attValue = new Value();
                attValue.setAttId(attribute.getId());
                attValue.setEntityId(orderItem.getId());
                switch (attribute.getAttName()) {
                    case "order_item_orderid": {
                        attValue.setValue(String.valueOf(orderEntity.getId()));
                        break;
                    }
                    case "order_item_name": {
                        attValue.setValue(String.valueOf(currProduct.getName()));
                        break;
                    }
                    case "order_item_price": {
                        attValue.setValue(String.valueOf(currProduct.getPrice()));
                        break;
                    }
                    case "order_item_count": {
                        attValue.setValue(String.valueOf(cartItem.getCounter()));
                        break;
                    }
                }
                valueMapper.insertValue(attValue);
            }

        }
        cartItemMapper.deleteAllUserCartItems(user.getId());
        commitAndCloseConnection();
        return result;
    }

    public List<Order> getAllOrders() {
        initConnection();
        List<Order> result = orderMapper.getAllOrders();
        commitAndCloseConnection();
        return result;
    }

    public List<Order> getOrdersByUserId(long id) {
        initConnection();
        List<Order> result = orderMapper.getOrdersByUserId(id);
        commitAndCloseConnection();
        return result;
    }

    public List<OrderItem> getAllOrderItemsByOrderID(long id) {
        initConnection();
        List<OrderItem> result = orderMapper.getAllOrderItemsByOrderID(id);
        commitAndCloseConnection();
        return result;
    }

    public Order getAllOrders(long id) {
        initConnection();
        Order result = orderMapper.getOrderById(id);
        commitAndCloseConnection();
        return result;
    }

    public Order getOrderById(long id) {
        initConnection();
        Order result = orderMapper.getOrderById(id);
        commitAndCloseConnection();
        return result;
    }

    public int changeOrderStatus(long id,String status){
        initConnection();
        Attribute statusAtt = attributeMapper.getAttributeByName("order_status");
        Value statusValue = new Value();
        statusValue.setValue(status);
        statusValue.setAttId(statusAtt.getId());
        statusValue.setEntityId(id);
        int result = valueMapper.updateValue(statusValue);
        commitAndCloseConnection();
        return result;
    }

    //---------------------------------------Images
    public int insertImage(Image image) {
        int result;
        initConnection();
        result = imagesMapper.insertImage(image);
        commitAndCloseConnection();
        return result;
    }

    public List<Image> getImagesByEnId(long id) {
        initConnection();
        List<Image> result = imagesMapper.getImagesByEnId(id);
        commitAndCloseConnection();
        return result;
    }

    public int deleteImageById(long id) {
        int result;
        initConnection();
        result = imagesMapper.deleteImageById(id);
        commitAndCloseConnection();
        return result;
    }

    public int updateImage(Image image) {
        int result;
        initConnection();
        result = imagesMapper.updateImage(image);
        commitAndCloseConnection();
        return result;
    }
}
