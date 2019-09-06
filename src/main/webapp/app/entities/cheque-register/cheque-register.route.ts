import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChequeRegister } from 'app/shared/model/cheque-register.model';
import { ChequeRegisterService } from './cheque-register.service';
import { ChequeRegisterComponent } from './cheque-register.component';
import { ChequeRegisterDetailComponent } from './cheque-register-detail.component';
import { ChequeRegisterUpdateComponent } from './cheque-register-update.component';
import { ChequeRegisterDeletePopupComponent } from './cheque-register-delete-dialog.component';
import { IChequeRegister } from 'app/shared/model/cheque-register.model';

@Injectable({ providedIn: 'root' })
export class ChequeRegisterResolve implements Resolve<IChequeRegister> {
    constructor(private service: ChequeRegisterService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChequeRegister> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ChequeRegister>) => response.ok),
                map((chequeRegister: HttpResponse<ChequeRegister>) => chequeRegister.body)
            );
        }
        return of(new ChequeRegister());
    }
}

export const chequeRegisterRoute: Routes = [
    {
        path: '',
        component: ChequeRegisterComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ChequeRegisters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ChequeRegisterDetailComponent,
        resolve: {
            chequeRegister: ChequeRegisterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChequeRegisters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ChequeRegisterUpdateComponent,
        resolve: {
            chequeRegister: ChequeRegisterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChequeRegisters'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ChequeRegisterUpdateComponent,
        resolve: {
            chequeRegister: ChequeRegisterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChequeRegisters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chequeRegisterPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ChequeRegisterDeletePopupComponent,
        resolve: {
            chequeRegister: ChequeRegisterResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChequeRegisters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
