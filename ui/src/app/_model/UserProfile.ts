import {Recipe} from "./Recipe";
import {Opinion} from "./Opinion";

export interface UserProfile {
  name: string,
  email: string,
  recipes: Recipe[],
  opinions: Opinion[]
}
