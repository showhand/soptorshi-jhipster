import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequisitionDetails } from 'app/shared/model/requisition-details.model';

import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';
import { Requisition } from 'app/shared/model/requisition.model';
import {
    RequisitionDetailsDeletePopupComponent,
    RequisitionDetailsResolve,
    RequisitionDetailsService
} from 'app/entities/requisition-details';
import { RequisitionDetailsExtendedComponent } from 'app/entities/requisition-details-extended/requisition-details-extended.component';
import { RequisitionDetailsExtendedDetailComponent } from 'app/entities/requisition-details-extended/requisition-details-extended-detail.component';
import { RequisitionDetailsExtendedUpdateComponent } from 'app/entities/requisition-details-extended/requisition-details-extended-update.component';

@Injectable({ providedIn: 'root' })
export class RequisitionDetailsExtendedResolve {
    constructor(public service: RequisitionDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisitionDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        const requisitionId = route.params['requisitionId'] ? route.params['requisitionId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RequisitionDetails>) => response.ok),
                map((requisitionDetails: HttpResponse<RequisitionDetails>) => requisitionDetails.body)
            );
        } else if (requisitionId) {
            const requisitionDetails = new RequisitionDetails();
            requisitionDetails.requisitionId = requisitionId;
            return of(requisitionDetails);
        }
        return of(new RequisitionDetails());
    }
}

export const requisitionDetailsExtendedRoute: Routes = [
    {
        path: 'home',
        component: RequisitionDetailsExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/requisition',
        component: RequisitionDetailsExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            requisitionDetails: RequisitionDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RequisitionDetailsExtendedDetailComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/new',
        component: RequisitionDetailsExtendedUpdateComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionDetailsExtendedUpdateComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionDetailsExtendedUpdateComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionDetailsExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionDetailsDeletePopupComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
