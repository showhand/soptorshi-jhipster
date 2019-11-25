/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SalaryMessagesDeleteDialogComponent } from 'app/entities/salary-messages/salary-messages-delete-dialog.component';
import { SalaryMessagesService } from 'app/entities/salary-messages/salary-messages.service';

describe('Component Tests', () => {
    describe('SalaryMessages Management Delete Component', () => {
        let comp: SalaryMessagesDeleteDialogComponent;
        let fixture: ComponentFixture<SalaryMessagesDeleteDialogComponent>;
        let service: SalaryMessagesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SalaryMessagesDeleteDialogComponent]
            })
                .overrideTemplate(SalaryMessagesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalaryMessagesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalaryMessagesService);
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
