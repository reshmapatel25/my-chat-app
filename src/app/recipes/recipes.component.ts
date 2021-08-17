import { Component, OnInit } from '@angular/core';
import { Recipe } from '../recipe';
//import { RECIPES } from '../mock-recipes';
import { RecipeService } from '../recipe.service';
//import {MessageService} from '../message.service';


@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss']
})
export class RecipesComponent implements OnInit {
  recipes: Recipe[];
  
  constructor(private recipeService: RecipeService) { }

  ngOnInit() {
    this.getRecipes();
  }
  getRecipes(): void {
    this.recipeService.getRecipes()
    .subscribe(recipes => this.recipes = recipes);
  }
  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.recipeService.addRecipe({ name } as Recipe)
      .subscribe(recipe => {
        this.recipes.push(recipe);
      });
  }
  delete(recipe: Recipe): void {
    this.recipes = this.recipes.filter(h => h !== recipe);
    this.recipeService.deleteRecipe(recipe).subscribe();
  }
  
  
}

/* Workshop-4
export class RecipesComponent implements OnInit {
  selectedRecipe: Recipe;
  recipes: Recipe[];

  constructor(private recipeService: RecipeService, private messageService: MessageService) { }


  ngOnInit() { this.getRecipes();  }
 
   onSelect(recipe: Recipe): void {
    this.selectedRecipe = recipe;
    this.messageService.add(`RecipeService: Selected recipe id=${recipe.id}`);
    
  }
 
  getRecipes(): void {
    this.recipeService.getRecipes()
        .subscribe(recipes => this.recipes = recipes);
  }
}*/

//Workshop- 3
/*export class RecipesComponent implements OnInit {
  //recipe='Indian Curry';
  /*recipe: Recipe = {
    id: 1,
    name: 'Indian Curry'
  };*/
  // recipes=RECIPES;
  
  /*recipes: Recipe[];
  
  selectedRecipe: Recipe;

  /*constructor(private recipeService: RecipeService)  { }

  /* Synchronous way
  
  getRecipes(): void {
    this.recipes = this.recipeService.getRecipes();
  }*/

  // Asynchronous way

  /*getRecipes(): void {
    this.recipeService.getRecipes()
        .subscribe(recipes => this.recipes = recipes);
  }
  
  
  ngOnInit(): void {
    this.getRecipes();
  }

  onSelect(recipe: Recipe): void {
    this.selectedRecipe = recipe;
  }

}
*/