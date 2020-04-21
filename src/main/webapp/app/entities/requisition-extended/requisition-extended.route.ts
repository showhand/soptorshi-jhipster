import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Requisition } from 'app/shared/model/requisition.model';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionDeletePopupComponent, RequisitionResolve, RequisitionService } from 'app/entities/requisition';
import { RequisitionExtendedDetailComponent } from 'app/entities/requisition-extended/requisition-extended-detail.component';
import { RequisitionExtendedUpdateComponent } from 'app/entities/requisition-extended/requisition-extended-update.component';
import { RequisitionExtendedComponent } from 'app/entities/requisition-extended/requisition-extended.component';

@Injectable({ providedIn: 'root' })
export class RequisitionExtendedResolve {
    constructor(public service: RequisitionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequisition> {
        const id = route.params['id'] ? route.params['id'] : null;
        const commercialId = route.params['commercialId'] ? route.params['commercialId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Requisition>) => response.ok),
                map((requisition: HttpResponse<Requisition>) => requisition.body)
            );
        } else if (commercialId) {
            const requisition = new Requisition();
            requisition.commercialId = commercialId;
            return of(requisition);
        }
        return of(new Requisition());
    }
}

export const requisitionExtendedRoute: Routes = [
    {
        path: '',
        component: RequisitionExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,desc',
            pageTitle: 'Requisitions',
            breadcrumb: 'Requisition'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RequisitionExtendedDetailComponent,
        resolve: {
            requisition: RequisitionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RequisitionExtendedUpdateComponent,
        resolve: {
            requisition: RequisitionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'commercial/:commercialId/new',
        component: RequisitionExtendedUpdateComponent,
        resolve: {
            requisition: RequisitionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RequisitionExtendedUpdateComponent,
        resolve: {
            requisition: RequisitionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requisitionExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RequisitionDeletePopupComponent,
        resolve: {
            requisition: RequisitionExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Requisitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
