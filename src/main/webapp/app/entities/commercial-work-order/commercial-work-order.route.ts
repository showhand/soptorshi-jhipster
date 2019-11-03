import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialWorkOrder, ICommercialWorkOrder } from 'app/shared/model/commercial-work-order.model';
import { CommercialWorkOrderService } from './commercial-work-order.service';
import { CommercialWorkOrderComponent } from './commercial-work-order.component';
import { CommercialWorkOrderDetailComponent } from './commercial-work-order-detail.component';
import { CommercialWorkOrderUpdateComponent } from './commercial-work-order-update.component';
import { CommercialWorkOrderDeletePopupComponent } from './commercial-work-order-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialWorkOrderResolve implements Resolve<ICommercialWorkOrder> {
    constructor(private service: CommercialWorkOrderService) {}

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

export const commercialWorkOrderRoute: Routes = [
    {
        path: '',
        component: CommercialWorkOrderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialWorkOrderDetailComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialWorkOrderUpdateComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialWorkOrderUpdateComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialWorkOrderPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialWorkOrderDeletePopupComponent,
        resolve: {
            commercialWorkOrder: CommercialWorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialWorkOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
