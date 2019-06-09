import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { FineAdvanceLoanManagementService } from './fine-advance-loan-management.service';
import { FineAdvanceLoanManagementComponent } from './fine-advance-loan-management.component';
import { FineAdvanceLoanManagementDetailComponent } from './fine-advance-loan-management-detail.component';
import { FineAdvanceLoanManagementUpdateComponent } from './fine-advance-loan-management-update.component';
import { FineAdvanceLoanManagementDeletePopupComponent } from './fine-advance-loan-management-delete-dialog.component';
import { IFineAdvanceLoanManagement } from 'app/shared/model/fine-advance-loan-management.model';
import { JhiResolvePagingParams } from 'ng-jhipster';

@Injectable({ providedIn: 'root' })
export class FineAdvanceLoanManagementResolve implements Resolve<IFineAdvanceLoanManagement> {
    constructor(private service: FineAdvanceLoanManagementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFineAdvanceLoanManagement> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            const fineAdvanceLoanManagement = new FineAdvanceLoanManagement();
            fineAdvanceLoanManagement.id = id;
            return of(fineAdvanceLoanManagement);
        }
        return of(new FineAdvanceLoanManagement());
    }
}

export const fineAdvanceLoanManagementRoute: Routes = [
    {
        path: '',
        component: FineAdvanceLoanManagementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            defaultSort: 'id,asc',
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FineAdvanceLoanManagementDetailComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve,
            pagingParams: JhiResolvePagingParams,
            finePagingParams: JhiResolvePagingParams,
            loanPagingParams: JhiResolvePagingParams,
            advancePagingParams: JhiResolvePagingParams,
            providentPagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_ADMIN'],
            pageTitle: 'FineAdvanceLoanManagements',
            defaultSort: 'id,asc'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FineAdvanceLoanManagementUpdateComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FineAdvanceLoanManagementUpdateComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fineAdvanceLoanManagementPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FineAdvanceLoanManagementDeletePopupComponent,
        resolve: {
            fineAdvanceLoanManagement: FineAdvanceLoanManagementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FineAdvanceLoanManagements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
