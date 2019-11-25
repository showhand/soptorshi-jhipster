import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesService } from './salary-messages.service';
import { SalaryMessagesComponent } from './salary-messages.component';
import { SalaryMessagesDetailComponent } from './salary-messages-detail.component';
import { SalaryMessagesUpdateComponent } from './salary-messages-update.component';
import { SalaryMessagesDeletePopupComponent } from './salary-messages-delete-dialog.component';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';

@Injectable({ providedIn: 'root' })
export class SalaryMessagesResolve implements Resolve<ISalaryMessages> {
    constructor(private service: SalaryMessagesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalaryMessages> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalaryMessages>) => response.ok),
                map((salaryMessages: HttpResponse<SalaryMessages>) => salaryMessages.body)
            );
        }
        return of(new SalaryMessages());
    }
}

export const salaryMessagesRoute: Routes = [
    {
        path: '',
        component: SalaryMessagesComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SalaryMessagesDetailComponent,
        resolve: {
            salaryMessages: SalaryMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaryMessagesUpdateComponent,
        resolve: {
            salaryMessages: SalaryMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaryMessagesUpdateComponent,
        resolve: {
            salaryMessages: SalaryMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const salaryMessagesPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SalaryMessagesDeletePopupComponent,
        resolve: {
            salaryMessages: SalaryMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
