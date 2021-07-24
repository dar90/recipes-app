import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "./AppSettings";
import {Router} from "@angular/router";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {AppUser} from "./AppUser";

@Injectable()
export class AuthService {

  private loggedInUserSubject: BehaviorSubject<AppUser>;
  private loginErrorsSubject: Subject<string>;

  constructor(private httpClient: HttpClient,
              private router: Router) {
    const localUserData: any = JSON.parse(localStorage.getItem('recipesAppUser') ?? '{}');
    const user: AppUser = {
      token: localUserData?.token ?? '',
      username: localUserData?.username ?? '',
      role: localUserData?.role ?? 'NOT_LOGGED_IN',
      expiration: localUserData?.expiration ?? -1
    };

    this.loggedInUserSubject = new BehaviorSubject<AppUser>(user);
    this.loginErrorsSubject = new Subject();
  }

  login(login: string, password: string) {
    this.httpClient.post(AppSettings.API_URL + '/user/login',
      { login: login, password: password },{responseType: 'text'})
      .subscribe(
        token => {
          this.setToken(token);
          this.router.navigate(['']);
        },
        error => {
          this.loginErrorsSubject.next('Nie udało się zalogować!');
          console.log(error);
        }
        );
  }

  private setToken(token: string) {
    const claims = token.split('.')[1];
    const decodedClaims = JSON.parse(window.atob(claims));
    const user: AppUser = {
      token: token,
      username: decodedClaims?.name ?? '',
      role: decodedClaims?.role ?? 'NOT_LOGGED_IN',
      expiration: decodedClaims?.exp ?? -1
    };
    this.loggedInUserSubject.next(user);
    localStorage.setItem('recipesAppUser', JSON.stringify(user));
  }

  logout() {
    localStorage.removeItem('recipesAppUser');
    this.loggedInUserSubject.next(
      {
        token: '',
        username: '',
        role: 'NOT_LOGGED_IN',
        expiration: -1
      }
    );
    this.router.navigate(['']);
  }

  get currentUser$(): Observable<AppUser> {
    return this.loggedInUserSubject.asObservable();
  }

  get loginErrors$(): Observable<string> {
    return this.loginErrorsSubject.asObservable();
  }

}
