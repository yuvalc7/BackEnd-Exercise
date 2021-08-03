package sparkbeyond.recruitment.exercise.superstar.candidate.server
import sparkbeyond.recruitment.exercise.superstar.candidate.logic.Stub

object Server extends cask.MainRoutes {

  @cask.get("/keys")
  def listHashKeys(): String = {
    // list registered hash keys, their creation time, and algorithm used. DO NOT RETURN SECRET SALT VALUES!
    try {
      Stub.GetHashKeysListRegister()
    }catch {
      case e => cask.Abort(500).statusCode.+(": " + e)
    }
  }

  @cask.postJson("/register/:hashKey")
  def register(hashKey: String, salt: Int, algorithm: String): String = {//request: cask.Request//String
    // register a new hashing key that persists internally an algorithm and a salt for consistent hashing by key
    try {
      Stub.Register(hashKey , salt.toString(), algorithm);
    }catch {
      case e => cask.Abort(422).statusCode.+(": " + e);
    }
  }

  @cask.post("/hash/:hashKey")
  def hash(hashKey: String, request: cask.Request): String = {//request: cask.Request

    //return Stub.Register(hashKey, "1234", "SHA-384");
    try {
      Stub.GetHashByKey(hashKey)
    }catch {
      case e => cask.Abort(404).statusCode.+(": " + e);
    }
  }

  initialize()
}
