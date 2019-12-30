import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesExtendedService } from './purchase-order-messages-extended.service';
import { PurchaseOrderMessagesExtendedComponent } from './purchase-order-messages-extended.component';
import { PurchaseOrderMessagesExtendedDetailComponent } from './purchase-order-messages-extended-detail.component';
import { PurchaseOrderMessagesExtendedUpdateComponent } from './purchase-order-messages-extended-update.component';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesDeletePopupComponent, PurchaseOrderMessagesResolve } from 'app/entities/purchase-order-messages';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderMessagesExtendedResolve implements Resolve<IPurchaseOrderMessages> {
    constructor(private service: PurchaseOrderMessagesExtendedService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseOrderMessages> {
        const id = route.params['id'] ? route.params['id'] : null;
        const purchaseOrderId = route.params['purchaseOrderId'] ? route.params['purchaseOrderId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchaseOrderMessages>) => response.ok),
                map((purchaseOrderMessages: HttpResponse<PurchaseOrderMessages>) => purchaseOrderMessages.body)
            );
        } else if (purchaseOrderId) {
            const purchaseOrderMessages = new PurchaseOrderMessages();
            purchaseOrderMessages.purchaseOrderId = purchaseOrderId;
            return of(purchaseOrderMessages);
        }
        return of(new PurchaseOrderMessages());
    }
}

export const purchaseOrderMessagesExtendedRoute: Routes = [
    {
        path: '',
        component: PurchaseOrderMessagesExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: PurchaseOrderMessagesExtendedDetailComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseOrderMessagesExtendedUpdateComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':purchaseOrderId/new',
        component: PurchaseOrderMessagesExtendedUpdateComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseOrderMessagesExtendedUpdateComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseOrderMessagesExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: PurchaseOrderMessagesDeletePopupComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
