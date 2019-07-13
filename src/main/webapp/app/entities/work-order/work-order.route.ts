import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { WorkOrder } from 'app/shared/model/work-order.model';
import { WorkOrderService } from './work-order.service';
import { WorkOrderComponent } from './work-order.component';
import { WorkOrderDetailComponent } from './work-order-detail.component';
import { WorkOrderUpdateComponent } from './work-order-update.component';
import { WorkOrderDeletePopupComponent } from './work-order-delete-dialog.component';
import { IWorkOrder } from 'app/shared/model/work-order.model';

@Injectable({ providedIn: 'root' })
export class WorkOrderResolve implements Resolve<IWorkOrder> {
    constructor(private service: WorkOrderService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWorkOrder> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<WorkOrder>) => response.ok),
                map((workOrder: HttpResponse<WorkOrder>) => workOrder.body)
            );
        }
        return of(new WorkOrder());
    }
}

export const workOrderRoute: Routes = [
    {
        path: '',
        component: WorkOrderComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'WorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: WorkOrderDetailComponent,
        resolve: {
            workOrder: WorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: WorkOrderUpdateComponent,
        resolve: {
            workOrder: WorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOrders'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: WorkOrderUpdateComponent,
        resolve: {
            workOrder: WorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workOrderPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: WorkOrderDeletePopupComponent,
        resolve: {
            workOrder: WorkOrderResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
