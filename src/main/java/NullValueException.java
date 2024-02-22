public class NullValueException extends Exception{
    @Override
    public String getMessage(){
        return "Value cannot be null";
    }
}
