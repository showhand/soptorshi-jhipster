/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { AdvanceManagementDeleteDialogComponent } from 'app/entities/advance-management/advance-management-delete-dialog.component';
import { AdvanceManagementService } from 'app/entities/advance-management/advance-management.service';

describe('Component Tests', () => {
    describe('AdvanceManagement Management Delete Component', () => {
        let comp: AdvanceManagementDeleteDialogComponent;
        let fixture: ComponentFixture<AdvanceManagementDeleteDialogComponent>;
        let service: AdvanceManagementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AdvanceManagementDeleteDialogComponent]
            })
                .overrideTemplate(AdvanceManagementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AdvanceManagementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvanceManagementService);
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
