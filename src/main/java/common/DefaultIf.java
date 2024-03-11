package common;

import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DefaultIf {

   public static BiFunction<Double,Double,Double> defaultIfNull =(in, def) -> Objects.isNull(in) ? def:in;
   public static BiFunction<Integer,Integer,Integer> defaultIfNullInteger=(v, d) -> Objects.isNull(v)?d:v;
   public static BiFunction<Double,Double,Double> defaultIfNullDouble=(v, d) -> Objects.isNull(v)?d:v;
   public static BiFunction<Object,Object,Object> defaultIfNullObject=(v, d) -> Objects.isNull(v)?d:v;


   public static BiFunction<List<Double>,List<Double>,List<Double>> defListIfNull=(l, dl) -> Objects.isNull(l)?dl:l;
   public static Function<List<Double>,List<Double>> emptyListIfNull=(l) -> defListIfNull.apply(l,new ArrayList<>());

   public static final TriFunction<Boolean,Object,Object,Object> secIfFalse =(cond, ifTrue, ifFalse) ->
           Boolean.TRUE.equals(cond) ? ifTrue:ifFalse;

   public static final TriFunction<Boolean,Double,Double,Double> secDoubleIfFalse =(cond, ifTrue, ifFalse) ->
           Boolean.TRUE.equals(cond) ? ifTrue:ifFalse;


}
