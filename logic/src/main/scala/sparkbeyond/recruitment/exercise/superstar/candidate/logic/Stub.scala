package sparkbeyond.recruitment.exercise.superstar.candidate.logic

import scala.collection.mutable.HashMap
import scala.collection.Set
import java.security.MessageDigest
import java.math.BigInteger
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
    //  val algorithm: String  =
        hashMap.get(key) match {
        case Some(str) => {
          strResult = strResult + "[ " + key + " , " + str.split("/")(1) + " , " +hashKeyTimestamp(index) + " ] ";
          index = index+1;
//          str.split("/")(1);
        }
        case None => throw new Exception("An Error Occurred, Pleas Try Again In A Few Minutes ");
      }
//      strResult = strResult + "[ " + key + " , " + algorithm + " , " +hashKeyTimestamp(index) + " ] ";
//      index = index+1;
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

  def GetHashByKey(hashKey: String): String ={

      val saltAndAlgorithm: Array[String] = hashMap.get(hashKey) match {
        case Some(str) => str.split("/");
        case None => throw new Exception("Non Existing Hash Key");
      }
      println(saltAndAlgorithm(0), saltAndAlgorithm(1).trim());
      val currentAlgorithm = MessageDigest.getInstance(saltAndAlgorithm(1).trim());
      currentAlgorithm.update(saltAndAlgorithm(0).getBytes());
     // val digest = currentAlgorithm.digest(hashKey.getBytes)
//      println(digest)
//      val hashBase64 = Base64.getEncoder.encode(digest)
//      println(hashBase64)
      val digest = currentAlgorithm.digest(hashKey.getBytes)
      val bigInt = new BigInteger(1, digest)
      val hashedString = bigInt.toString(16)
     // hashBase64.toString
      hashedString
//      BaseEncoding.base64()
//        .encode("user:pass".getBytes(Charsets.UTF_8)));
   // }

   // "Key Not Exist"
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

  def GetHashByKey(hashKey: String ) : String = {
     obj.GetHashByKey(hashKey);
  }
}
