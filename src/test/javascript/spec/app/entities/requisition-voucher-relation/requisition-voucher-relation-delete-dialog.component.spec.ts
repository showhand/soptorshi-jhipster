/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionVoucherRelationDeleteDialogComponent } from 'app/entities/requisition-voucher-relation/requisition-voucher-relation-delete-dialog.component';
import { RequisitionVoucherRelationService } from 'app/entities/requisition-voucher-relation/requisition-voucher-relation.service';

describe('Component Tests', () => {
    describe('RequisitionVoucherRelation Management Delete Component', () => {
        let comp: RequisitionVoucherRelationDeleteDialogComponent;
        let fixture: ComponentFixture<RequisitionVoucherRelationDeleteDialogComponent>;
        let service: RequisitionVoucherRelationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionVoucherRelationDeleteDialogComponent]
            })
                .overrideTemplate(RequisitionVoucherRelationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionVoucherRelationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionVoucherRelationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
