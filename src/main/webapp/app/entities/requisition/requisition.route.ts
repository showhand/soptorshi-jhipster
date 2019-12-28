import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Requisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from './requisition.service';
import { RequisitionComponent } from './requisition.component';
import { RequisitionDetailComponent } from './requisition-detail.component';
import { RequisitionUpdateComponent } from './requisition-update.component';
import { RequisitionDeletePopupComponent } from './requisition-delete-dialog.component';
import { IRequisition } from 'app/shared/model/requisition.model';

@Injectable({ providedIn: 'root' })
export class RequisitionResolve implements Resolve<IRequisition> {
    constructor(private service: RequisitionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Requisition>) => response.ok),
                map((requisition: HttpResponse<Requisition>) => requisition.body)
            );
        }
        return of(new Requisition());
    }
}

export const requisitionRoute: Routes = [
    {
        path: '',
        component: RequisitionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RequisitionDetailComponent,
        resolve: {
            requisition: RequisitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionUpdateComponent,
        resolve: {
            requisition: RequisitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionUpdateComponent,
        resolve: {
            requisition: RequisitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionDeletePopupComponent,
        resolve: {
            requisition: RequisitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
