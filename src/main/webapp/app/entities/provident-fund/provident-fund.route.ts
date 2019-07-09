import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProvidentFund } from 'app/shared/model/provident-fund.model';
import { ProvidentFundService } from './provident-fund.service';
import { ProvidentFundComponent } from './provident-fund.component';
import { ProvidentFundDetailComponent } from './provident-fund-detail.component';
import { ProvidentFundUpdateComponent } from './provident-fund-update.component';
import { ProvidentFundDeletePopupComponent } from './provident-fund-delete-dialog.component';
import { IProvidentFund } from 'app/shared/model/provident-fund.model';

@Injectable({ providedIn: 'root' })
export class ProvidentFundResolve implements Resolve<IProvidentFund> {
    constructor(private service: ProvidentFundService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProvidentFund> {
        const id = route.params['id'] ? route.params['id'] : null;
        const employeeLongId = route.params['employeeLongId'] ? route.params['employeeLongId'] : null;
        const employeeId = route.params['employeeId'] ? route.params['employeeId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProvidentFund>) => response.ok),
                map((providentFund: HttpResponse<ProvidentFund>) => providentFund.body)
            );
        } else if (employeeLongId) {
            const providentFund: IProvidentFund = new ProvidentFund();
            providentFund.employeeId = employeeLongId;
            return of(providentFund);
        } else if (employeeId) {
            const providentFund: IProvidentFund = new ProvidentFund();
            providentFund.employeeId = employeeId;
            return of(providentFund);
        }
        return of(new ProvidentFund());
    }
}

export const providentFundRoute: Routes = [
    {
        path: '',
        component: ProvidentFundComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeLongId/employee',
        component: ProvidentFundComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            providentFund: ProvidentFundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProvidentFundDetailComponent,
        resolve: {
            providentFund: ProvidentFundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':employeeId/new',
        component: ProvidentFundUpdateComponent,
        resolve: {
            providentFund: ProvidentFundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProvidentFundUpdateComponent,
        resolve: {
            providentFund: ProvidentFundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProvidentFundUpdateComponent,
        resolve: {
            providentFund: ProvidentFundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const providentFundPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProvidentFundDeletePopupComponent,
        resolve: {
            providentFund: ProvidentFundResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProvidentFunds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
