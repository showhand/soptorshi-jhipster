import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialAttachment, ICommercialAttachment } from 'app/shared/model/commercial-attachment.model';
import { CommercialAttachmentService } from './commercial-attachment.service';
import { CommercialAttachmentComponent } from './commercial-attachment.component';
import { CommercialAttachmentDetailComponent } from './commercial-attachment-detail.component';
import { CommercialAttachmentUpdateComponent } from './commercial-attachment-update.component';
import { CommercialAttachmentDeletePopupComponent } from './commercial-attachment-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialAttachmentResolve implements Resolve<ICommercialAttachment> {
    constructor(private service: CommercialAttachmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialAttachment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialAttachment>) => response.ok),
                map((commercialAttachment: HttpResponse<CommercialAttachment>) => commercialAttachment.body)
            );
        }
        return of(new CommercialAttachment());
    }
}

export const commercialAttachmentRoute: Routes = [
    {
        path: '',
        component: CommercialAttachmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialAttachmentDetailComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialAttachmentUpdateComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialAttachmentUpdateComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialAttachmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialAttachmentDeletePopupComponent,
        resolve: {
            commercialAttachment: CommercialAttachmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialAttachments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
