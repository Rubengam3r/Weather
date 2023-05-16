package com.rubenmobile.weather;

import android.content.Context;

public class GetContext
{
   public static MainActivity context;

   private GetContext()
   {

   }

   public static void setContext(Context c) {context = (MainActivity) c; }
}
