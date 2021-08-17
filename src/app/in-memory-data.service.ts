import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Recipe } from './recipe';
@Injectable({  providedIn: 'root', })
export class InMemoryDataService implements InMemoryDbService {
 
  createDb() {   
    const recipes = [ 
      { id: 11, name: 'Butter Paneer' },
      { id: 12, name: 'Vegetable Korma' },
      { id: 13, name: 'Paneer Capsicum' },
      { id: 14, name: 'Kadai Paneer' },
      { id: 15, name: 'Paneer Angara' },
      { id: 16, name: 'Rumali Roti' },
      { id: 17, name: 'Chapati' },
      { id: 18, name: 'Butter Naan' },
      { id: 19, name: 'Gulab Jamun' },
      { id: 20, name: 'Rabdi' }   
    ];
        return {recipes}; 
  }
    // Overrides the genId method to ensure that a recipe always has an id.
  // If the recipes array is empty,
  // the method below returns the initial number (11).
  // if the recipes array is not empty, the method below returns the highest
  // recipe id + 1.
  genId(recipes: Recipe[]): number {
    return recipes.length > 0 ? Math.max(...recipes.map(recipe => recipe.id)) + 1 : 11;
  }
}
