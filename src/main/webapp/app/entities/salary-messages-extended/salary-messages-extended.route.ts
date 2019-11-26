import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesExtendedService } from './salary-messages-extended.service';
import { SalaryMessagesExtendedComponent } from './salary-messages-extended.component';
import { SalaryMessagesExtendedDetailComponent } from './salary-messages-extended-detail.component';
import { SalaryMessagesExtendedUpdateComponent } from './salary-messages-extended-update.component';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';
import { SalaryMessagesDeletePopupComponent } from 'app/entities/salary-messages';

@Injectable({ providedIn: 'root' })
export class SalaryMessagesExtendedResolve implements Resolve<ISalaryMessages> {
    constructor(private service: SalaryMessagesExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISalaryMessages> {
        const id = route.params['id'] ? route.params['id'] : null;
        const monthlySalaryId = route.params['monthlySalaryId'] ? route.params['monthlySalaryId'] : null;

        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SalaryMessages>) => response.ok),
                map((salaryMessages: HttpResponse<SalaryMessages>) => salaryMessages.body)
            );
        } else if (monthlySalaryId) {
            const salaryMessages = new SalaryMessages();
            salaryMessages.monthlySalaryId = monthlySalaryId;
            return of(salaryMessages);
        }
        return of(new SalaryMessages());
    }
}

export const salaryMessagesExtendedRoute: Routes = [
    {
        path: '',
        component: SalaryMessagesExtendedComponent,
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
        component: SalaryMessagesExtendedDetailComponent,
        resolve: {
            salaryMessages: SalaryMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SalaryMessagesExtendedUpdateComponent,
        resolve: {
            salaryMessages: SalaryMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':monthlySalaryId/new',
        component: SalaryMessagesExtendedUpdateComponent,
        resolve: {
            salaryMessages: SalaryMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SalaryMessagesExtendedUpdateComponent,
        resolve: {
            salaryMessages: SalaryMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SalaryMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];
