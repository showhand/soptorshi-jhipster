import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesService } from './purchase-order-messages.service';
import { PurchaseOrderMessagesComponent } from './purchase-order-messages.component';
import { PurchaseOrderMessagesDetailComponent } from './purchase-order-messages-detail.component';
import { PurchaseOrderMessagesUpdateComponent } from './purchase-order-messages-update.component';
import { PurchaseOrderMessagesDeletePopupComponent } from './purchase-order-messages-delete-dialog.component';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';

@Injectable({ providedIn: 'root' })
export class PurchaseOrderMessagesResolve implements Resolve<IPurchaseOrderMessages> {
    constructor(private service: PurchaseOrderMessagesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseOrderMessages> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PurchaseOrderMessages>) => response.ok),
                map((purchaseOrderMessages: HttpResponse<PurchaseOrderMessages>) => purchaseOrderMessages.body)
            );
        }
        return of(new PurchaseOrderMessages());
    }
}

export const purchaseOrderMessagesRoute: Routes = [
    {
        path: '',
        component: PurchaseOrderMessagesComponent,
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
        component: PurchaseOrderMessagesDetailComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: PurchaseOrderMessagesUpdateComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: PurchaseOrderMessagesUpdateComponent,
        resolve: {
            purchaseOrderMessages: PurchaseOrderMessagesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PurchaseOrderMessages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const purchaseOrderMessagesPopupRoute: Routes = [
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
