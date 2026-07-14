package com.itheima.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


/**
 * 七牛云工具类
 */
public class QiniuUtils
{

    //AK密钥
    public static String accessKey =
            "Iu0Bdc-HOBEFkXenO3VIP1CtKe9VeIevdp6ATfi7";
    //SK密钥
    public static String secretKey =
            "-lwzBCiVfWUoqMSdpjEls8rxP6ddgLLjjBINGfJm";
    //空间名
    public static String bucket = "javaee-health-zlx01";
    //上传文件
    public static void upload2Qiniu(byte[] bytes, String fileName)
    {

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());

        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);//鉴权
        String upToken = auth.uploadToken(bucket);//用于文件上传的token
        try
        {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(
                    response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex)
        {
            Response r = ex.response;
            System.err.println(r.toString());
            try
            {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
            }
        }
    }

    //删除文件
    public static void deleteFileFromQiniu(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        String key = fileName;//指定文件名
        Auth auth = Auth.create(accessKey, secretKey);//鉴权
        //获取存储空间
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);//删除指定图片
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
