import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialWorkOrder, ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderExtendedService } from './commercial-work-order-extended.service';
import { CommercialWorkOrderExtendedComponent } from './commercial-work-order-extended.component';
import { CommercialWorkOrderDetailExtendedComponent } from './commercial-work-order-detail-extended.component';
import { CommercialWorkOrderUpdateExtendedComponent } from './commercial-work-order-update-extended.component';
import { CommercialWorkOrderDeletePopupExtendedComponent } from './commercial-work-order-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialWorkOrderExtendedResolve implements Resolve<ICommercialWorkOrder> {
    constructor(private service: CommercialWorkOrderExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialWorkOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialWorkOrder>) => response.ok),
                map((commercialWorkOrder: HttpResponse<CommercialWorkOrder>) => commercialWorkOrder.body)
            );
        }
        return of(new CommercialWorkOrder());
    }
}

export const commercialWorkOrderExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialWorkOrderExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialWorkOrderDetailExtendedComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialWorkOrderUpdateExtendedComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialWorkOrderUpdateExtendedComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialWorkOrderPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialWorkOrderDeletePopupExtendedComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
