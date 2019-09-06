/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { DtTransactionDeleteDialogComponent } from 'app/entities/dt-transaction/dt-transaction-delete-dialog.component';
import { DtTransactionService } from 'app/entities/dt-transaction/dt-transaction.service';

describe('Component Tests', () => {
    describe('DtTransaction Management Delete Component', () => {
        let comp: DtTransactionDeleteDialogComponent;
        let fixture: ComponentFixture<DtTransactionDeleteDialogComponent>;
        let service: DtTransactionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [DtTransactionDeleteDialogComponent]
            })
                .overrideTemplate(DtTransactionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DtTransactionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DtTransactionService);
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
