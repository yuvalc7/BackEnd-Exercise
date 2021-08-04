package sparkbeyond.recruitment.exercise.superstar.candidate.logic

import scala.collection.mutable.HashMap
import java.security.{MessageDigest, NoSuchAlgorithmException}
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Base64
import scala.util.{Failure, Success, Try};

class Stub {
  // placeholder for actual logic code

  private var hashMap: HashMap[String,String] = new HashMap[String, String];

  //really efficient data stricter without fix size in aspect of memory
  private var hashKeyTimestamp: Vector[String] = Vector();

  private def Register(hashKey: String, salt:String, algorithm:String):String  = {

    hashMap.get(hashKey) match {
      case Some(str) => throw new Exception("This Key Already Exist, Please Try Register With Different Key !")
      case None => StoreDataOrThrow(hashKey,algorithm,algorithm)
    }
  }

  /**
   * this function check if algorithm exist => store in hashmap[String,String] as
   * key=>hashKey, value=> salt / algorithm
   * else throw Exception
   * @param hashKey = entry key
   * @param salt =  value to store as string
   * @param algorithm = value value to store as string
   * @return String or Exception
   */
  private def StoreDataOrThrow(hashKey: String, salt:String, algorithm:String): String={
    try{
      MessageDigest.getInstance(algorithm)
      hashMap.put(hashKey, salt + " / " + algorithm);
      val timeStamp: String = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(new Date);
      hashKeyTimestamp = hashKeyTimestamp :+ timeStamp;
      "Register Successfully";
    }
    catch {
      case e @ (_: NoSuchAlgorithmException ) => throw new RuntimeException("No Such Algorithm", e)
    }
  }

  /**
   * this function iterate hashmap keys , for each key append to listRegisterStr [key , algorithm, createdTime]
   * if map is empty return No Registration are made
   * @return string
   */
  private def GetHashKeysListRegister (): String={
    val sb:StringBuilder = new StringBuilder()
    var index: Int = 0;
    hashMap.keys.foreach { (key) =>
        hashMap.get(key) match {
        case Some(str) => {
          val algorithm: String = str.split("/")(1);
          sb.append("[ " + key + " , " + algorithm + " , " +hashKeyTimestamp(index) + " ] ");
          index = index+1;
        }
        case None => throw new Exception("An Error Occurred, Pleas Try Again In A Few Minutes ");
      }
    };
    if(sb.isEmpty){ return "No Registration are made" };
    sb.toString()
  }

  private def GetHashByKey(hashKey: String, secret: String): String ={

        val saltAndAlgorithm: Array[String] = hashMap.get(hashKey) match {
        case Some(str) => str.split("/");
        case None => throw new RuntimeException("Non Existing Hash Key");
      }
      try {
        val salt: String = saltAndAlgorithm(0);
        val algorithm: String = saltAndAlgorithm(1).trim();
        val currentAlgorithm: MessageDigest = MessageDigest.getInstance(algorithm);
        currentAlgorithm.update(salt.getBytes());// equal to salt + secret
        val bytes: Array[Byte]  = currentAlgorithm.digest(secret.getBytes(StandardCharsets.UTF_8))
        Base64.getEncoder.encodeToString(bytes)
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
