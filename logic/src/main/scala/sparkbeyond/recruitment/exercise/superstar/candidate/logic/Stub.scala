package sparkbeyond.recruitment.exercise.superstar.candidate.logic

import sun.jvm.hotspot.HelloWorld.e

import scala.collection.mutable.HashMap
import java.security.{MessageDigest, NoSuchAlgorithmException}
import java.nio.charset.StandardCharsets
import java.security.spec.InvalidKeySpecException
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import java.util.Base64;

class Stub {
  // placeholder for actual logic code

  var hashMap: HashMap[String,String] = new HashMap[String, String];

  var hashKeyTimestamp: Vector[String] = Vector();


  def GetHashKeysListRegister () : String={
    var strResult : String = "";
    var index: Int = 0;
    hashMap.keys.foreach { (key) =>
        hashMap.get(key) match {
        case Some(str) => {
          strResult = strResult + "[ " + key + " , " + str.split("/")(1) + " , " +hashKeyTimestamp(index) + " ] ";
          index = index+1;
        }
        case None => throw new Exception("An Error Occurred, Pleas Try Again In A Few Minutes ");
      }
    };
    strResult ;
  }

  def Register(hashKey: String, salt:String, algorithm:String):String  = {

      hashMap.get(hashKey) match {
        case Some(str) => throw new Exception("This Key Already Exist, Please Try Register With Different Key !")
        case None => {
          val timeStamp = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date);
          hashMap.put(hashKey, salt + " / " + algorithm);
          hashKeyTimestamp = hashKeyTimestamp :+ timeStamp;
          "Register Successfully";
        }
      }
  }

  def GetHashByKey(hashKey: String, secret: String): String ={

      val saltAndAlgorithm: Array[String] = hashMap.get(hashKey) match {
        case Some(str) => str.split("/");
        case None => throw new RuntimeException("Non Existing Hash Key");
      }
      try {
        val currentAlgorithm = MessageDigest.getInstance(saltAndAlgorithm(1).trim());
        currentAlgorithm.update(saltAndAlgorithm(0).getBytes());// equal to salt + secret
        val bytes: Array[Byte]  = currentAlgorithm.digest(secret.getBytes(StandardCharsets.UTF_8))
        Base64.getEncoder.encodeToString(bytes)
      }catch {
        case e @ (_: NoSuchAlgorithmException ) => throw new RuntimeException("No Such Algorithm", e)
      }
  }
}

object Stub {
  //singleton class
  private val obj = new Stub();

  def Register(hashKey: String, salt: String, algorithm: String): String ={
     obj.Register(hashKey, salt, algorithm)
  }
  def GetHashKeysListRegister(): String ={
     obj.GetHashKeysListRegister()
  }

  def GetHashByKey(hashKey: String, secret: String ) : String = {
     obj.GetHashByKey(hashKey,secret);
  }
}
