/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { JournalVoucherDeleteDialogComponent } from 'app/entities/journal-voucher/journal-voucher-delete-dialog.component';
import { JournalVoucherService } from 'app/entities/journal-voucher/journal-voucher.service';

describe('Component Tests', () => {
    describe('JournalVoucher Management Delete Component', () => {
        let comp: JournalVoucherDeleteDialogComponent;
        let fixture: ComponentFixture<JournalVoucherDeleteDialogComponent>;
        let service: JournalVoucherService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [JournalVoucherDeleteDialogComponent]
            })
                .overrideTemplate(JournalVoucherDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JournalVoucherDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalVoucherService);
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
