package com.icycraft.league_lecture.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.league_lecture.dao.ClazzDao;
import com.icycraft.league_lecture.dao.ClazzFaqDao;
import com.icycraft.league_lecture.dao.CreateDao;
import com.icycraft.league_lecture.dao.UserDao;
import com.icycraft.league_lecture.entity.Clazz;
import com.icycraft.league_lecture.entity.ClazzFaq;
import com.icycraft.league_lecture.entity.CreateReq;
import com.icycraft.league_lecture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService{

    @Autowired
    ClazzDao clazzDao;


    @Autowired
    UserDao userDao;


    @Autowired
    CreateDao createDao;


    @Autowired
    ClazzFaqDao clazzFaqDao;

    @Autowired
    MailService mailService;

    @Value("${yl2wx.file.path}")
    private String ewmPath;

    @Override
    public Clazz getClazz(long id) {

        Clazz clazz = clazzDao.selectById(id);
        if (clazz!=null){

            QueryWrapper<User> uqw = new QueryWrapper<>();

            uqw.eq("clazz_id", clazz.getId());

            Integer uqwCount = userDao.selectCount(uqw);

            clazz.setRealPnum(uqwCount);
        }
        return clazz;
    }

    @Override
    public Clazz updateClazz(Clazz clazz) {
        clazzDao.updateById(clazz);
        return clazz;
    }


    @Override
    public Clazz validClazzCode(long clazzId,String code) {

        Clazz clazz = clazzDao.selectById(clazzId);

        if (code.equals(clazz.getCode())){
            return clazz;
        }else {
            return null;
        }


    }

    @Override
    public List<Clazz> getClazzs() {
        return clazzDao.selectList(new QueryWrapper<>());
    }

    @Override
    public Clazz getClazzByUserId(Long userId) {
        return clazzDao.selectOne(new QueryWrapper<Clazz>().eq("user_id",userId));
    }

    @Override
    public List<ClazzFaq> getClazzFaqs() {
        return clazzFaqDao.selectList(null);
    }

    @Override
    public CreateReq addCreateInfo(CreateReq create) {
        List<CreateReq> createReqs = createDao.selectList(new QueryWrapper<CreateReq>().eq("wx_id", create.getWxId()).eq("status",0));
        for (CreateReq createReq : createReqs) {
            createDao.deleteById(createReq.getId());
        }
        createDao.insert(create);
        return create;
    }

    @Override
    public List<CreateReq> getCreateReqsUnDo() {

        QueryWrapper<CreateReq> cqw = new QueryWrapper<>();
        cqw.eq("status",0);

        return createDao.selectList(cqw);
    }

    @Override
    public void dealCreateReq(int reqId, int status) {
        CreateReq createReq = createDao.selectById(reqId);
        createReq.setStatus(status);
        String clazzName = createReq.getSchool()+createReq.getClazz();
        if (status==1){
            //通过
            //班级id和wxpicker序列对应，id必须严格排序
            Integer count = clazzDao.selectCount(null);



            Clazz clazz = new Clazz();
            clazz.setId(count);
            clazz.setRealPnum(0);
            clazz.setCode("1234");
            clazz.setPnum(0);
            clazz.setName(clazzName);
            clazzDao.insert(clazz);

            long clazzId = clazz.getId();
            QueryWrapper<User> uqw = new QueryWrapper<>();

            //给用户赋权限
            uqw.eq("wx_id",createReq.getWxId());

            User user = userDao.selectOne(uqw);

            user.setClazzId(clazzId);

            user.setMail(createReq.getMail());

            user.setRoleId(1);

            user.setName(createReq.getName());

            userDao.updateById(user);

            String subject = "来自青年大学习截图小助手的消息，你申请的班级"+clazzName+"已通过";
            String content = "  你好，"+user.getName()+"，你已经获取"+clazz.getName()+"团支书权限，请重新进入小程序查看，并及时修改班级口令以及其他信息，另附小程序二维码，请查收附件，现在可以通过此二维码邀请你的同学们提交截图，记得告诉他们班级口令，有任何问题请及时联系作者。";
            mailService.sendAttachmentsMail(user.getMail(),subject,content,ewmPath);
        }else if (status==2){
            //拒绝
            String subject = "来自青年大学习截图小助手的消息，你申请的班级"+clazzName+"未通过";
            String content = "  你好，"+createReq.getName()+"，很抱歉你申请的班级未能通过，有任何问题请及时联系作者。";
            mailService.sendMail(createReq.getMail(),subject,content);
        }
        createDao.updateById(createReq);
    }


}
