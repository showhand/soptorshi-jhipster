import { AfterViewInit, Component, ElementRef, Renderer } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';
import { LoginService, StateStorageService } from 'app/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiLoginModalComponent } from 'app/shared';

@Component({
    selector: 'jhi-login',
    templateUrl: './login-extended.component.html'
})
export class JhiLoginExtendedComponent implements AfterViewInit {
    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;

    constructor(
        public eventManager: JhiEventManager,
        public loginService: LoginService,
        public stateStorageService: StateStorageService,
        public elementRef: ElementRef,
        public renderer: Renderer,
        public router: Router
    ) {
        this.credentials = {};
    }

    ngAfterViewInit() {
        setTimeout(() => this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []), 0);
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
    }

    login() {
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                this.router.navigate(['']);

                /*
                this.activeModal.dismiss('login success');
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }
*/

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                // location.reload();
                const redirect = this.stateStorageService.getUrl();
                if (redirect) {
                    this.stateStorageService.storeUrl(null);
                    this.router.navigate([redirect]);
                }
            })
            .catch(() => {
                this.authenticationError = true;
            });
    }

    requestResetPassword() {
        // this.activeModal.dismiss('to state requestReset');
        this.router.navigate(['/reset', 'request']);
    }

    /*register() {
      this.activeModal.dismiss('to state register');
      this.router.navigate(['/register']);
    }*/
}
