import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { AboutComponent } from './about/about.component';
import { RecipesComponent } from './recipes/recipes.component';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';
import { ProfileComponent } from './profile/profile.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { RecipeComponent } from './recipe/recipe.component';
import { DatePipe } from '@angular/common';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AboutComponent,
    RecipesComponent,
    AddRecipeComponent,
    ProfileComponent,
    UserProfileComponent,
    RecipeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [DatePipe, HttpClientModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
