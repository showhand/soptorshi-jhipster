import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialPurchaseOrder, ICommercialPurchaseOrder } from 'app/shared/model/commercial-purchase-order.model';
import { CommercialPurchaseOrderExtendedService } from './commercial-purchase-order-extended.service';
import { CommercialPurchaseOrderExtendedComponent } from './commercial-purchase-order-extended.component';
import { CommercialPurchaseOrderDetailExtendedComponent } from './commercial-purchase-order-detail-extended.component';
import { CommercialPurchaseOrderUpdateExtendedComponent } from './commercial-purchase-order-update-extended.component';
import { CommercialPurchaseOrderDeletePopupExtendedComponent } from './commercial-purchase-order-delete-dialog-extended.component';

@Injectable({ providedIn: 'root' })
export class CommercialPurchaseOrderExtendedResolve implements Resolve<ICommercialPurchaseOrder> {
    constructor(private service: CommercialPurchaseOrderExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialPurchaseOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialPurchaseOrder>) => response.ok),
                map((commercialPurchaseOrder: HttpResponse<CommercialPurchaseOrder>) => commercialPurchaseOrder.body)
            );
        }
        return of(new CommercialPurchaseOrder());
    }
}

export const commercialPurchaseOrderExtendedRoute: Routes = [
    {
        path: '',
        component: CommercialPurchaseOrderExtendedComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialPurchaseOrderDetailExtendedComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialPurchaseOrderUpdateExtendedComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialPurchaseOrderUpdateExtendedComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialPurchaseOrderPopupExtendedRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialPurchaseOrderDeletePopupExtendedComponent,
        resolve: {
            commercialPurchaseOrder: CommercialPurchaseOrderExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialPurchaseOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
