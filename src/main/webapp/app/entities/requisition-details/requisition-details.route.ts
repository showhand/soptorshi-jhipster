import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RequisitionDetails } from 'app/shared/model/requisition-details.model';
import { RequisitionDetailsService } from './requisition-details.service';
import { RequisitionDetailsComponent } from './requisition-details.component';
import { RequisitionDetailsDetailComponent } from './requisition-details-detail.component';
import { RequisitionDetailsUpdateComponent } from './requisition-details-update.component';
import { RequisitionDetailsDeletePopupComponent } from './requisition-details-delete-dialog.component';
import { IRequisitionDetails } from 'app/shared/model/requisition-details.model';

@Injectable({ providedIn: 'root' })
export class RequisitionDetailsResolve implements Resolve<IRequisitionDetails> {
    constructor(private service: RequisitionDetailsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisitionDetails> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RequisitionDetails>) => response.ok),
                map((requisitionDetails: HttpResponse<RequisitionDetails>) => requisitionDetails.body)
            );
        }
        return of(new RequisitionDetails());
    }
}

export const requisitionDetailsRoute: Routes = [
    {
        path: '',
        component: RequisitionDetailsComponent,
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
        path: ':id/view',
        component: RequisitionDetailsDetailComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionDetailsUpdateComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionDetailsUpdateComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionDetailsPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionDetailsDeletePopupComponent,
        resolve: {
            requisitionDetails: RequisitionDetailsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequisitionDetails'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
