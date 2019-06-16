/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentManagementDeleteDialogComponent } from 'app/entities/provident-management/provident-management-delete-dialog.component';
import { ProvidentManagementService } from 'app/entities/provident-management/provident-management.service';

describe('Component Tests', () => {
    describe('ProvidentManagement Management Delete Component', () => {
        let comp: ProvidentManagementDeleteDialogComponent;
        let fixture: ComponentFixture<ProvidentManagementDeleteDialogComponent>;
        let service: ProvidentManagementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentManagementDeleteDialogComponent]
            })
                .overrideTemplate(ProvidentManagementDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProvidentManagementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProvidentManagementService);
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
